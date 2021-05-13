package ebraheem.agroscout.data.model

data class Post(
    var title: String = "",
    var created: Long,
    var created_utc: Long,
    var author: String,
    var permalink: String,
    var id: String = "",
    var name: String = "",
    var thumbnail: String? = null,
    var url_overridden_by_dest: String? = null,
    var url: String? = null,
    var icon_url: String,
    var num_comments: Long,
    var ups: Long
)