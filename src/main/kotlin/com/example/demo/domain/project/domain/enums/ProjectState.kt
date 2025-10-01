package com.archisketch.dashboard.domain.project.enums

enum class ProjectState(
    val value: Int,
    val description: String,
) {
    ACTIVATED(0, "활성화"),
    DEACTIVATED(1, "휴지통"),
    DELETED(2, "완전삭제");

    companion object {
        fun of(value: Int): ProjectState {
            return when (value) {
                0 -> ACTIVATED
                1 -> DEACTIVATED
                2 -> DELETED
                else -> throw NoSuchElementException("Invalid value for ProjectState")
            }
        }
    }
}
