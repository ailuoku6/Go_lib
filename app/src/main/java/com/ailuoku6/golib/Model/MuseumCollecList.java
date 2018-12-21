package com.ailuoku6.golib.Model;

import java.util.ArrayList;
import java.util.List;

public class MuseumCollecList {
    private List<MuseumCollection> museumCollectionList;
    public MuseumCollecList(){
        museumCollectionList = new ArrayList<>();
    }

    public List<MuseumCollection> getMuseumCollectionList() {
        return museumCollectionList;
    }

    public void addItem(MuseumCollection m){
        museumCollectionList.add(m);
    }

    public void cleanList(){
        museumCollectionList.clear();
    }
}
