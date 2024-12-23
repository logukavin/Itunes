package com.sample.itunes.model

import com.google.gson.annotations.SerializedName

data class AllResponse(

	@field:SerializedName("resultCount")
	val resultCount: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItems?>? = null
)

data class ResultsItems(

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

	@field:SerializedName("collectionArtistId")
	val collectionArtistId: Int? = null,

	@field:SerializedName("collectionArtistViewUrl")
	val collectionArtistViewUrl: String? = null,

	@field:SerializedName("advisories")
	val advisories: List<Any?>? = null,

	@field:SerializedName("userRatingCountForCurrentVersion")
	val userRatingCountForCurrentVersion: Int? = null,

	@field:SerializedName("screenshotUrls")
	val screenshotUrls: List<String?>? = null,

	@field:SerializedName("ipadScreenshotUrls")
	val ipadScreenshotUrls: List<String?>? = null,

	@field:SerializedName("averageUserRatingForCurrentVersion")
	val averageUserRatingForCurrentVersion: Double? = null,

	@field:SerializedName("sellerName")
	val sellerName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("primaryGenreId")
	val primaryGenreId: Int? = null,

	@field:SerializedName("features")
	val features: List<String?>? = null,

	@field:SerializedName("releaseNotes")
	val releaseNotes: String? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("trackContentRating")
	val trackContentRating: String? = null,

	@field:SerializedName("currentVersionReleaseDate")
	val currentVersionReleaseDate: String? = null,

	@field:SerializedName("userRatingCount")
	val userRatingCount: Int? = null,

	@field:SerializedName("isVppDeviceBasedLicensingEnabled")
	val isVppDeviceBasedLicensingEnabled: Boolean? = null,

	@field:SerializedName("contentAdvisoryRating")
	val contentAdvisoryRating: String? = null,

	@field:SerializedName("minimumOsVersion")
	val minimumOsVersion: String? = null,

	@field:SerializedName("isGameCenterEnabled")
	val isGameCenterEnabled: Boolean? = null,

	@field:SerializedName("bundleId")
	val bundleId: String? = null,

	@field:SerializedName("languageCodesISO2A")
	val languageCodesISO2A: List<String?>? = null,

	@field:SerializedName("genreIds")
	val genreIds: List<String?>? = null,

	@field:SerializedName("artworkUrl512")
	val artworkUrl512: String? = null,

	@field:SerializedName("version")
	val version: String? = null,

	@field:SerializedName("appletvScreenshotUrls")
	val appletvScreenshotUrls: List<Any?>? = null,

	@field:SerializedName("fileSizeBytes")
	val fileSizeBytes: String? = null,

	@field:SerializedName("formattedPrice")
	val formattedPrice: String? = null,

	@field:SerializedName("supportedDevices")
	val supportedDevices: List<String?>? = null,

	@field:SerializedName("averageUserRating")
	val averageUserRating: Double? = null,

	@field:SerializedName("sellerUrl")
	val sellerUrl: String? = null,

	@field:SerializedName("collectionHdPrice")
	val collectionHdPrice: Double? = null,

	@field:SerializedName("artworkUrl600")
	val artworkUrl600: String? = null,

	@field:SerializedName("feedUrl")
	val feedUrl: String? = null
)
