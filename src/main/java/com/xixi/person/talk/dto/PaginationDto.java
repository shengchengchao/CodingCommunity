package com.xixi.person.talk.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xixi-98
 * @Date: 2019/12/18 23:18
 * @Description:
 */
public class PaginationDto {
    //是否显示第一页
    private Boolean showFirstPage;
    //是否显示最后一页
    private Boolean showEndPage;
    //是否显示下一页
    private Boolean showNextPage;
    //是否显示上一页
    private Boolean showPreviousPage;
    //当前页数
    private Integer page;
    //总页数
    private Integer totalPage;
    //需要显示的页码
    private List<Integer> pages=new ArrayList<>();
    //需要显示的问题
    private List<QuestionDto> questionList=new ArrayList<>();

    public void setpagination(Integer size,Integer page,int totalPage){
        this.page=page;
        this.totalPage=totalPage;

        // 第一页的 不显示上一页与前一页
        showFirstPage = (page == 1) ? false: true;
        showPreviousPage = (page == 1) ? false: true;

        //最后一页 不显示最后一页与下一页
        showNextPage = (page == totalPage) ? false :true;
        showEndPage = (page == totalPage) ? false :true;

        for(int i = 1;i<=2;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if (page-i == 1){
                showFirstPage=false;
            }
        }
        pages.add(page);
        for(int i = 1;i <= 2;i++){
            if(page+i < totalPage){
                pages.add(page+i);
            }
            if (page +i == totalPage){
                pages.add(page+i);
                showEndPage=false;
            }
        }

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public List<QuestionDto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionDto> questionList) {
        this.questionList = questionList;
    }

    public Boolean getShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(Boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public Boolean getShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(Boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Boolean getShowNextPage() {
        return showNextPage;
    }

    public void setShowNextPage(Boolean showNextPage) {
        this.showNextPage = showNextPage;
    }

    public boolean isShowPreviousPage() {
        return showPreviousPage;
    }

    public void setShowPreviousPage(boolean showPreviousPage) {
        this.showPreviousPage = showPreviousPage;
    }
}
