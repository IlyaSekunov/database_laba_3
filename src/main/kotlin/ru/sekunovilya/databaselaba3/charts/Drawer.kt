package ru.sekunovilya.databaselaba3.charts

import com.aspose.cells.Chart
import com.aspose.cells.ChartType
import com.aspose.cells.SaveFormat
import com.aspose.cells.Workbook
import com.aspose.cells.Worksheet
import ru.sekunovilya.databaselaba3.benchmark.config.Library
import ru.sekunovilya.databaselaba3.config.RESULTS_PATH
import ru.sekunovilya.databaselaba3.connectors.QueryType
import java.io.File

data class ConnectorResults(
    val library: Library,
    val queryResults: Map<QueryType, Double>
)

enum class ResultExtension {
    XLSX
}

fun drawChart(results: List<ConnectorResults>, resultExtension: ResultExtension) =
    when (resultExtension) {
        ResultExtension.XLSX -> drawChartAsXls(results)
    }

private fun createFile(filePath: String) = File(filePath).createNewFile()

private fun createFileName(results: List<ConnectorResults>) =
    with(StringBuilder()) {
        results.forEach { result ->
            append(result.library.name)
            append("_")
        }
        setLength(length - 1)
        this
    }.toString()

private fun drawChartAsXls(results: List<ConnectorResults>) {
    fun fillCategories(worksheet: Worksheet) {
        worksheet.cells["A2"].putValue("Query 1")
        worksheet.cells["A3"].putValue("Query 2")
        worksheet.cells["A4"].putValue("Query 3")
        worksheet.cells["A5"].putValue("Query 4")
    }

    fun fillConnectorResults(worksheet: Worksheet) {
        results.forEachIndexed { index, result ->
            val column = 'B' + index
            worksheet.cells["${column}1"].putValue(result.library.name)
            val sortedResults = result.queryResults.toSortedMap()
            sortedResults.forEach { (query, result) ->
                worksheet.cells["$column${query.ordinal + 2}"].putValue((result / 1000))
            }
        }
    }

    fun createChart(worksheet: Worksheet) =
        worksheet.charts.add(ChartType.COLUMN, 6, 0, 40, 15)

    fun setChartDataBounds(chart: Chart) {
        val chartLeftUpperCell = "A1"
        val chartRightDownCell = "${'B' + results.size}5"
        chart.setChartDataRange("$chartLeftUpperCell:$chartRightDownCell", true)
    }

    val fileName = createFileName(results)
    val filePath = "$RESULTS_PATH$fileName.xlsx"
    createFile(filePath)

    val workbook = Workbook(filePath)
    val worksheet = workbook.worksheets[0]
    worksheet.name = "results"

    fillCategories(worksheet)
    fillConnectorResults(worksheet)

    val chartIndex = createChart(worksheet)
    val chart = worksheet.charts[chartIndex]
    setChartDataBounds(chart)
    workbook.save(filePath, SaveFormat.XLSX)
}