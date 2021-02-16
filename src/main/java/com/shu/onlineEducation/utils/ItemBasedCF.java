package com.shu.onlineEducation.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.util.List;

public class ItemBasedCF {
	
	public static void main(String[] args) throws Exception {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("hyq20000719");
		dataSource.setDatabaseName("online_education");
		DataModel model = new MySQLJDBCDataModel(dataSource, "course_comment", "student_id", "course_id", "comment_mark", "time");
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