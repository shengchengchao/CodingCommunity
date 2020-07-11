package com.xixi.person.talk.utils;



import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pageutils<T> {
    private List<T> pageLists = new ArrayList<>();
    private boolean PreviousPage;
    private boolean FirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer pages;
    //导航页集合
    private List<Integer> navigatepageNums = new ArrayList<Integer>();
    //总页数
    private Integer totalPage;

    private Integer pageSize;

    public void setPagination(Integer pageSize, Integer page,List<T> list){
        if(pageSize<0){
            this.pageSize=3;
        }else {
            this.pageSize=pageSize;
        }
        if(list.size()%pageSize == 0){
            this.totalPage=list.size()/pageSize;
        }else{
            this.totalPage = list.size()/pageSize+1;
        }
        if(page<0){
            this.pages = 1;
        }else if(page>0){
            this.pages = page;
        }else if(page>totalPage){
            this.pages=totalPage;
        }
        /**
         * 导入集合
         * 第一个要考虑的是:数据集合小于当前页 显示最后一页的数据
         * 第二 数据集合大小大于当前页 正常显示
         * 第三 数据集合小于当前页 但是有一部分数据 显示一部分数据
         */
        if(list.size()>=pages*pageSize){
            for(int i = (pages-1)*pageSize;i<page*pageSize;i++){
                pageLists.add(list.get(i));
            }
        }else if(list.size()<pages*pageSize){
            if(list.size()>(pages-1)*pageSize){
                for(int i = (pages-1)*pageSize;i<list.size();i++){
                    pageLists.add(list.get(i));
                }
            }
        }


        navigatepageNums.add(page);
        for (int i = 1; i <= 5; i++) {
            if (page - i > 0) {
                navigatepageNums.add(0, page - i);
            }

            if (page + i <= totalPage) {
                navigatepageNums.add(page + i);
            }
        }

        // 是否展示上一页
        if (page == 1) {
            PreviousPage = false;
        } else {
            PreviousPage = true;
        }

        // 是否展示下一页
        if (page.equals(totalPage)) {
            showNext = false;
        } else {
            showNext = true;
        }

        // 是否展示第一页
        if (navigatepageNums.contains(1)) {
            FirstPage = false;
        } else {
            FirstPage = true;
        }

        // 是否展示最后一页
        if (navigatepageNums.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
