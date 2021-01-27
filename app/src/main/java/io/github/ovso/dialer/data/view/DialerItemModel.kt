package io.github.ovso.dialer.data.view

data class DialerItemModel(
  val contactId: Long,
  val name: String,
  val no: String,
  val footer: Boolean = false,
  val color: String,
  val groupId:Long,
) {
  companion object {
    fun empty(): DialerItemModel = DialerItemModel(
      contactId = -1,
      name = "",
      no = "",
      footer = true,
      color = "",
      groupId = -1
    )
  }
}
