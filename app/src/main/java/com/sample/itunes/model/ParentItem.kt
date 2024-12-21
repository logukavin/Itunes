package com.sample.itunes.model

data class ParentItem(
    val kind: String,
    val children: List<ChildItem>
) {
    // Override equals and hashCode to compare based on relevant fields
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ParentItem
        return kind == other.kind // Assuming `kind` is the key to identify duplicates
    }

    override fun hashCode(): Int {
        return kind.hashCode()
    }
}

data class ChildItem(
    val collectionName: String,
    val artworkUrl: String
) {
    // Override equals and hashCode to compare based on relevant fields
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ChildItem
        return collectionName == other.collectionName && artworkUrl == other.artworkUrl
    }

    override fun hashCode(): Int {
        return collectionName.hashCode() * 31 + artworkUrl.hashCode()
    }
}
