package com.sample.itunes.model

import com.google.gson.annotations.SerializedName

data class SearchMediaResponse(

	@field:SerializedName("resultCount")
	val resultCount: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("artworkUrl100")
	val artworkUrl100: String? = null,

	@field:SerializedName("trackTimeMillis")
	val trackTimeMillis: Int? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("previewUrl")
	val previewUrl: String? = null,

	@field:SerializedName("artistId")
	val artistId: Int? = null,

	@field:SerializedName("trackName")
	val trackName: String? = null,

	@field:SerializedName("collectionName")
	val collectionName: String? = null,

	@field:SerializedName("artistViewUrl")
	val artistViewUrl: String? = null,

	@field:SerializedName("discNumber")
	val discNumber: Int? = null,

	@field:SerializedName("trackCount")
	val trackCount: Int? = null,

	@field:SerializedName("artworkUrl30")
	val artworkUrl30: String? = null,

	@field:SerializedName("wrapperType")
	val wrapperType: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("collectionId")
	val collectionId: Int? = null,

	@field:SerializedName("isStreamable")
	val isStreamable: Boolean? = null,

	@field:SerializedName("trackExplicitness")
	val trackExplicitness: String? = null,

	@field:SerializedName("collectionViewUrl")
	val collectionViewUrl: String? = null,

	@field:SerializedName("trackNumber")
	val trackNumber: Int? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("kind")
	val kind: String? = null,

	@field:SerializedName("trackId")
	val trackId: Int? = null,

	@field:SerializedName("collectionPrice")
	val collectionPrice: Double? = null,

	@field:SerializedName("discCount")
	val discCount: Int? = null,

	@field:SerializedName("primaryGenreName")
	val primaryGenreName: String? = null,

	@field:SerializedName("trackPrice")
	val trackPrice: Double? = null,

	@field:SerializedName("collectionExplicitness")
	val collectionExplicitness: String? = null,

	@field:SerializedName("trackViewUrl")
	val trackViewUrl: String? = null,

	@field:SerializedName("artworkUrl60")
	val artworkUrl60: String? = null,

	@field:SerializedName("trackCensoredName")
	val trackCensoredName: String? = null,

	@field:SerializedName("artistName")
	val artistName: String? = null,

	@field:SerializedName("collectionCensoredName")
	val collectionCensoredName: String? = null,

	@field:SerializedName("collectionArtistName")
	val collectionArtistName: String? = null,

	@field:SerializedName("longDescription")
	val longDescription: String? = null,

	@field:SerializedName("trackHdRentalPrice")
	val trackHdRentalPrice: Double? = null,

	@field:SerializedName("collectionHdPrice")
	val collectionHdPrice: Double? = null,

	@field:SerializedName("trackHdPrice")
	val trackHdPrice: Double? = null,

	@field:SerializedName("contentAdvisoryRating")
	val contentAdvisoryRating: String? = null,

	@field:SerializedName("trackRentalPrice")
	val trackRentalPrice: Double? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("collectionArtistId")
	val collectionArtistId: Int? = null,

	@field:SerializedName("collectionArtistViewUrl")
	val collectionArtistViewUrl: String? = null,

	@field:SerializedName("hasITunesExtras")
	val hasITunesExtras: Boolean? = null
)
