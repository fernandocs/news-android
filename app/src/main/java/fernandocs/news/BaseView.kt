package fernandocs.news

interface BaseView<in T> {

    fun setPresenter(presenter: T)

}