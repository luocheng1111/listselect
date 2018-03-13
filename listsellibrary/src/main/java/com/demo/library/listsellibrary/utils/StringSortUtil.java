package com.demo.library.listsellibrary.utils;



import com.demo.library.listsellibrary.bean.SelListBean;
import com.github.promeg.pinyinhelper.Pinyin;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringSortUtil {

    /**
     * 按照列表中data指定指定字段进行字母升序排列
     *
     * @param datas
     */
    public void sortByLetter(List<SelListBean> datas) {
        for (SelListBean data : datas) {
            data.setTheFisrtPinYin(getFirstPinyin(data.getTitle()));
        }

        Collections.sort(datas, new Comparator<SelListBean>() {
            @Override
            public int compare(SelListBean o1, SelListBean o2) {
                if (o1.getTheFisrtPinYin().equals("#")) {
                    return 1;
                } else if (o2.getTheFisrtPinYin().equals("#")) {
                    return -1;
                } else {
                    return o1.getTheFisrtPinYin().compareTo(o2.getTheFisrtPinYin());
                }
            }
        });
    }

    //得到首字母 罗成->L
    public String getFirstPinyin(String str){
        String firstPinyin = "A";
        char fist = str.toCharArray()[0];
        if (String.valueOf(fist).matches("[A-Za-z]")) {
            firstPinyin = String.valueOf(fist).toUpperCase();
        } else if (Pinyin.isChinese(fist)) {
            firstPinyin = Pinyin.toPinyin(fist).substring(0, 1);
        } else {
            //特殊字符情况
            firstPinyin = ("#");
        }
        return firstPinyin;
    }

}
