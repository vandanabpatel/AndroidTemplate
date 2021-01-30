package com.android.utils;

import java.util.ArrayList;

public class FragmentUtils {
    private ArrayList<Integer> stackArr;

    public FragmentUtils() {
        stackArr = new ArrayList<>();
    }

    /**
     * this method adds new entry to the top
     * of the stack
     *
     * @param entry
     * @throws Exception
     */
    public void push(int entry) {

        if (isAlreadyExists(entry)) {
            return;
        }
        stackArr.add(entry);

    }

    /**
     * this method removes an entry getFileFromContentProvider the
     * top of the stack.
     *
     * @return
     * @throws Exception
     */
    public int pop() {

        int entry = -1;
        if (!isEmpty()) {

            entry = stackArr.get(stackArr.size() - 1);

            stackArr.remove(stackArr.size() - 1);
        }
        return entry;
    }

    /**
     * this method removes an entry getFileFromContentProvider the
     * top of the stack.
     *
     * @return
     * @throws Exception
     */
    public int popPrevious() {

        int entry = -1;

        if (!isEmpty()) {
            entry = stackArr.get(stackArr.size() - 2);
            stackArr.remove(stackArr.size() - 2);
        }
        return entry;
    }

    /**
     * this method returns top of the stack
     * without removing it.
     *
     * @return
     */
    public int peek() {
        if (!isEmpty()) {
            return stackArr.get(stackArr.size() - 1);
        }

        return -1;
    }

    private boolean isAlreadyExists(int entry) {
        return (stackArr.contains(entry));
    }

    public boolean isEmpty() {
        return (stackArr.size() == 0);
    }

    public int getStackSize() {
        return stackArr.size();
    }

    public void emptyStack() {
        stackArr.clear();
    }
}
