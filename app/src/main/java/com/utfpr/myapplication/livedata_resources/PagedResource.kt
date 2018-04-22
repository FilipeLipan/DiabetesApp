package com.utfpr.myapplication.livedata_resources

/**
 * Created by lispa on 16/12/2017.
 */
class PagedResource<T> private constructor(val status: Status, val data: T?, val newData: T?, val message: String?, val hasMorePages: Boolean) {
    companion object {

        fun <T> success(data: T, hasMorePages: Boolean): PagedResource<T> {
            return PagedResource(Status.SUCCESS, data, null, null, hasMorePages)
        }

        fun <T> successMoreData(oldData: T?, newData: T?, hasMorePages: Boolean): PagedResource<T> {
            val mergedLists: ArrayList<T> = ArrayList<T>()
            mergedLists.addAll(oldData!! as ArrayList<T>)
            mergedLists.addAll(newData!! as ArrayList<T>)
            return PagedResource(Status.SUCCESS_LOAD_MORE_DATA, oldData, newData, null, hasMorePages)
        }

        //TODO -- improve -- remove data from error function
        fun <T> error(msg: String, data: T): PagedResource<T> {
            return PagedResource(Status.ERROR, data, null, msg, false)
        }

        fun <T> loading(data: T?): PagedResource<T> {
            return PagedResource(Status.LOADING, data, null, null, false)
        }
    }
}
