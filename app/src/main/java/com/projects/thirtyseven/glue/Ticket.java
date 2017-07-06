package com.projects.thirtyseven.glue;

import java.util.ArrayList;

class Ticket {
    private String ticketDate;
    private String ticketTime;
    private String ticketTag;
    private String ticketCategory;
    private String ticketDescription;
    private String ticketTaskProfession;
    private String ticketTaskCoWorker;
    private String ticketTaskFee;
    private String ticketExpenses;
    private String ticketSpending;
    private String ticketComment;
    private String ticketTitle;
    private Post FBPost;

    public ArrayList<Author> getAuthor() {
        return author;
    }

    private ArrayList<Author> author;

    public Ticket() {

    }

    public Ticket(String ticketDate, String ticketTime, String ticketTag,
                  String ticketCategory, String ticketDescription,
                  String ticketTaskProfession, String ticketTaskCoWorker,
                  String ticketTaskFee, String ticketExpenses,
                  String ticketSpending, String ticketComment,
                  ArrayList<Author> author) {
        this.ticketDate = ticketDate;
        this.ticketTime = ticketTime;
        this.ticketTag = ticketTag;
        this.ticketCategory = ticketCategory;
        this.ticketDescription = ticketDescription;
        this.ticketTaskProfession = ticketTaskProfession;
        this.ticketTaskCoWorker = ticketTaskCoWorker;
        this.ticketTaskFee = ticketTaskFee;
        this.ticketExpenses = ticketExpenses;
        this.ticketSpending = ticketSpending;
        this.ticketComment = ticketComment;
        this.author = author;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public String getTicketTag() {
        return ticketTag;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public String getTicketTaskProfession() {
        return ticketTaskProfession;
    }

    public String getTicketTaskCoWorker() {
        return ticketTaskCoWorker;
    }

    public String getTicketTaskFee() {
        return ticketTaskFee;
    }

    public String getTicketExpenses() {
        return ticketExpenses;
    }

    public String getTicketSpending() {
        return ticketSpending;
    }

    public String getTicketComment() {
        return ticketComment;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public void setTicketTag(String ticketTag) {
        this.ticketTag = ticketTag;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public void setTicketTaskProfession(String ticketTaskProfession) {
        this.ticketTaskProfession = ticketTaskProfession;
    }

    public void setTicketTaskCoWorker(String ticketTaskCoWorker) {
        this.ticketTaskCoWorker = ticketTaskCoWorker;
    }


    public void setTicketTaskFee(String ticketTaskFee) {
        this.ticketTaskFee = ticketTaskFee;
    }

    public void setTicketExpenses(String ticketExpenses) {
        this.ticketExpenses = ticketExpenses;
    }

    public void setTicketSpending(String ticketSpending) {
        this.ticketSpending = ticketSpending;
    }

    public void setTicketComment(String ticketComment) {
        this.ticketComment = ticketComment;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public void setAuthor(ArrayList<Author> author) {
        this.author = author;
    }

    public void setFBPost(Post FBPost) {
        this.FBPost = FBPost;
    }

    public Post getFBPost() {
        return FBPost;
    }
}
