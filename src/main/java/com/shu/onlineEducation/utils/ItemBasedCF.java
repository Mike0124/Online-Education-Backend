package com.shu.onlineEducation.utils;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.shu.onlineEducation.dao.CourseJpaRepository;
import com.shu.onlineEducation.dao.ItemCFJpaRepository;
import com.shu.onlineEducation.dao.ItemCFResultJpaRepository;
import com.shu.onlineEducation.dao.StudentJpaRepository;
import com.shu.onlineEducation.entity.Course;
import com.shu.onlineEducation.entity.ItemCF;
import com.shu.onlineEducation.entity.ItemCFResult;
import com.shu.onlineEducation.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class ItemBasedCF {
	@Autowired
	StudentJpaRepository studentJpaRepository;
	@Autowired
	CourseJpaRepository courseJpaRepository;
	@Autowired
	ItemCFJpaRepository itemCFJpaRepository;
	@Autowired
	ItemCFResultJpaRepository itemCFResultJpaRepository;
	
	public static Integer RECOMMEND_LIST_SIZE = 4;
	
	public static Recommender getRecommender() throws TasteException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("hyq20000719");
		dataSource.setDatabaseName("online_education");
		DataModel model = new MySQLJDBCDataModel(dataSource, "item_based_cf", "student_id", "course_id", "cf_mark", "time");
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
		// 构造推荐引擎
		return new GenericItemBasedRecommender(model, similarity);
	}
	
	public List<Course> studentCourseCF(Recommender recommender, Student student) throws TasteException {
		int resultSize = 0;
		List<Course> resultList = new ArrayList<>();
		try {
			List<RecommendedItem> recommendedItemList = recommender.recommend(student.getUserId(), RECOMMEND_LIST_SIZE);
			resultSize = recommendedItemList.size();
			for (RecommendedItem recommendedItem : recommendedItemList) {
				Course course = courseJpaRepository.findByCourseId((int) recommendedItem.getItemID());
				if (course != null) {
					resultList.add(course);
					if (resultList.size() >= RECOMMEND_LIST_SIZE) {
						break;
					}
				}
			}
		} catch (NoSuchUserException noSuchUserException) {
			log.error(noSuchUserException.getMessage());
		}
		if (resultSize < RECOMMEND_LIST_SIZE) {
			HashSet<Course> courseHashSet = new HashSet<>();
			List<ItemCF> itemCFList = itemCFJpaRepository.findByStudentId(student.getUserId());
			for (ItemCF itemCF : itemCFList) {
				Course course = courseJpaRepository.findByCourseId(itemCF.getCourseId());
				if (course != null) {
					courseHashSet.add(course);
				}
			}
			List<Course> courseList = courseJpaRepository.findByMajorOrderByWatches(student.getMajorId());
			for (Course course : courseList) {
				if (!courseHashSet.contains(course)) {
					resultList.add(course);
					if (resultList.size() >= RECOMMEND_LIST_SIZE) {
						break;
					}
				}
			}
		}
		return resultList;
	}
	
	@Async
	public void asyncUpdateAll() throws TasteException {
		Recommender recommender = getRecommender();
		List<Student> allStudent = studentJpaRepository.findAll();
		for (Student student : allStudent) {
			List<Course> courseList = studentCourseCF(recommender, student);
			ItemCFResult itemCFResult = itemCFResultJpaRepository.findByStudentId(student.getUserId());
			if (itemCFResult == null) {
				itemCFResult = new ItemCFResult();
				itemCFResult.setStudentId(student.getUserId());
			}
			String jsonString = JSON.toJSONString(courseList);
			itemCFResult.setResult(jsonString);
			itemCFResultJpaRepository.save(itemCFResult);
		}
	}
	
	public static void main(String[] args) throws TasteException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("hyq20000719");
		dataSource.setDatabaseName("online_education");
		DataModel model = new MySQLJDBCDataModel(dataSource, "item_based_cf", "student_id", "course_id", "cf_mark", "time");
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
		// 构造推荐引擎
		Recommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		LongPrimitiveIterator iter = model.getUserIDs();
		
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			List<RecommendedItem> list = recommender.recommend(uid, 3);
			System.out.println("uid=" + uid + ":");
			for (RecommendedItem ritem : list) {
				System.out.println("[" + ritem.getItemID() + ":" + ritem.getValue() + "]");
			}
			System.out.println();
		}
	}
}