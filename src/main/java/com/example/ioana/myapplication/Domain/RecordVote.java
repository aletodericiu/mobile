package com.example.ioana.myapplication.Domain;

/**
 * Created by Ioana on 1/6/2018.
 */

public class RecordVote {

    private String id;
    private String recordName;
    private int vote;

    public RecordVote(String id, String recordName, int vote) {
        this.id=id;
        this.recordName = recordName;
        this.vote = vote;
    }
    public RecordVote() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
