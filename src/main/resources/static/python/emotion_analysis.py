from __future__ import unicode_literals
import json
import sys
import os
from snownlp import SnowNLP
import jieba

work_place = os.path.split(os.path.realpath(__file__))[0] + "/"


# 实词分词以及词频计算
def wc_and_analysis(list_):
    stopwords = [word.strip() for word in open(work_place + 'hit_stopwords.txt', encoding='UTF-8').readlines()]
    wc_dict = {}
    worst_comment = []
    avg_mark = 0
    for arr in list_:
        if len(arr) < 2:
            continue
        text = arr[0].lower()

        # 分词
        words_list = jieba.lcut(text)
        for word in words_list:
            if len(word) == 1:
                continue
            else:
                wc_dict[word] = wc_dict.get(word, 0) + 1

        # 情感分析
        s = SnowNLP(text)
        score = s.sentiments * int(arr[1])
        avg_mark = avg_mark + score
        arr.append(score)
        if score < 1:
            worst_comment.append(arr)

    wcls = [(key, value) for (key, value) in wc_dict.items() if key not in stopwords]
    wcls.sort(key=lambda x: x[1], reverse=True)
    worst_comment.sort(key=lambda x: x[1])
    return {'word_cut': wcls[:20:], 'worst_comment': worst_comment[:10:], "avg_mark": avg_mark / len(list_)}



if __name__ == '__main__':
    text_list = []
    string = sys.argv[1]
    for line in string.split("\n"):
        text_list.append(list(line.split("\t")))
    print(json.dumps(wc_and_analysis(text_list), ensure_ascii=False))
