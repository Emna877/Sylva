package com.example.sylva

import com.example.sylva.data.api.PlantIdClassification
import com.example.sylva.data.api.PlantIdResponse
import com.example.sylva.data.api.PlantIdResult
import com.example.sylva.data.api.PlantSuggestion
import com.example.sylva.data.repository.TreeRepository
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun findTopPlantSuggestion_scansAllResults() {
        val response = PlantIdResponse(
            results = listOf(
                PlantIdResult(),
                PlantIdResult(
                    classification = PlantIdClassification(
                        suggestions = listOf(
                            PlantSuggestion(legacyName = "Species A", probability = 0.55),
                            PlantSuggestion(legacyName = "Species B", probability = 0.91)
                        )
                    )
                )
            )
        )

        val suggestion = TreeRepository().findTopPlantSuggestion(response)

        assertNotNull(suggestion)
        val topSuggestion = suggestion!!
        assertEquals("Species B", topSuggestion.displayName)
        assertEquals(0.91, topSuggestion.probability, 0.0001)
    }
}