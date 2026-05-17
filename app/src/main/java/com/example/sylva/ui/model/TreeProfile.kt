package com.example.sylva.ui.model

data class InsightItem(
    val title: String,
    val body: String,
    val iconLabel: String
)

data class TreeProfile(
    val scientificName: String,
    val commonNames: List<String>,
    val confidence: Float,
    val nativeRegion: String,
    val biodiversityImportance: String,
    val urbanUsefulness: String,
    val ecologicalRole: String,
    val funFact: String,
    val insights: List<InsightItem>
)

object SampleTreeData {
    val oakProfile = TreeProfile(
        scientificName = "Quercus robur",
        commonNames = listOf("English Oak", "Pedunculate Oak"),
        confidence = 0.94f,
        nativeRegion = "Europe and Western Asia",
        biodiversityImportance = "Hosts hundreds of insect species and supports rich bird habitats.",
        urbanUsefulness = "Strong pollution tolerance and excellent shade coverage for heat reduction.",
        ecologicalRole = "Keystone canopy species that stabilizes soil and supports long-lived forest structure.",
        funFact = "Some oak trees can live for over 600 years and become mini ecosystems.",
        insights = listOf(
            InsightItem(
                title = "Habitat Value",
                body = "Its acorns and canopy structure support birds, mammals, and pollinators year-round.",
                iconLabel = "habitat"
            ),
            InsightItem(
                title = "Climate Resilience",
                body = "Deep roots improve drought resilience while broad leaves cool nearby urban zones.",
                iconLabel = "climate"
            ),
            InsightItem(
                title = "Soil Protection",
                body = "Leaf litter enriches organic matter and reduces soil erosion after heavy rain.",
                iconLabel = "soil"
            )
        )
    )
}

