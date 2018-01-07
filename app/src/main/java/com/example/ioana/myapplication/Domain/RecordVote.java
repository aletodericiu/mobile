package com.example.ioana.myapplication.Domain;

/**
 * Created by Ioana on 1/6/2018.
 */

public class RecordVote {

    private int id;
    private int recordId;
    private int vote;

    public RecordVote(int id, int recordId, int vote) {
        this.id=id;
        this.recordId = recordId;
        this.vote = vote;
    }
    public RecordVote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
