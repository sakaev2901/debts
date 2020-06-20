package ru.itis.dto;


public class MessageDto {
    private String pageId;
    private String text;

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPageId() {
        return pageId;
    }

    public String getText() {
        return text;
    }
}
