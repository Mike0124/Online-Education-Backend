import os
import sys

from snownlp import SnowNLP
from snownlp import sentiment

if __name__ == '__main__':
    work_place = os.path.split(os.path.realpath(__file__))[0]+"/"
    f = sys.argv[1]
    text_list = []
    for line in f.split("\n"):
        text_list.append(list(line.split("\t")))
    f1 = open(work_place + 'pos.txt', 'r+', encoding="utf-8")  # 存放正面  名字也可自定义哦
    f2 = open(work_place + 'neg.txt', 'r+', encoding="utf-8")  # 存放负面
    for list in text_list:
        text = list[0]
        s = SnowNLP(text)
        score = s.sentiments * int(list[1])
        print(text + "\t" + str(score), end='\t')  # 打印正负面属性值
        if score < 1:
            print('负面评价')
            # 这段文本写入neg文件中
            f2.write(text)
            f2.write('\n')

        elif score > 2:
            print('正面评价')
            # 这段文本写入pos文件中
            f1.write(text)
            f1.write('\n')
        else:
            print('中性评价')
    # 保存此次的训练模型
    sentiment.train(work_place + 'neg.txt', work_place + 'pos.txt')
    # 生成新的训练模型
    sentiment.save(work_place + 'sentiment.marshal')
    f1.truncate(0)
    f2.truncate(0)
    f.close()
    f1.close()
    f2.close()
