package com.soft507.travelsuggest.listener;

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/12 10:14
 */
public interface DragListener {
    /**
     * 是否将 item拖动到删除处，根据状态改变颜色
     *
     * @param isDelete
     */
    void deleteState(boolean isDelete);

    /**
     * 是否于拖拽状态
     *
     * @param start
     */
    void dragState(boolean isStart);
}
