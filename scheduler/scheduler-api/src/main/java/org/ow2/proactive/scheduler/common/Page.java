package org.ow2.proactive.scheduler.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T extends Serializable> implements Serializable {
    
    private int size;

    private ArrayList<T> list;
    
    public Page() {

    }
    
    public Page(List<T> list, int size) {
        this.list = new ArrayList<T>(list);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = new ArrayList<T>(list);
    }

    @Override
    public String toString() {
        return "TaskPage{" + "size=" + size + ", list='" + list + '\'' + '}';
    }
    
    

}
