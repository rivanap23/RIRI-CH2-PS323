package com.riridev.ririapp.data.dummy

import com.riridev.ririapp.data.model.Report

class ReportDummy {
    companion object {
        val listReport = listOf(
            Report(
                1,
                "Mobil tabrakan",
                "Polsek",
                "Kecelakaan",
                "Ini ada movil kecelakaan didepan",
                "Jalan kenangan indah",
                "12/11/2023"
            ),
            Report(
                2,
                "Mobil Nynagkut",
                "Polsek",
                "Kecelakaan",
                "Ini ada movil nyangkut didepan",
                "Jalan kenangan indah",
                "12/11/2023"
            ),
            Report(
                3,
                "Kebakaran",
                "Pemadam Kebakaran",
                "Kecelakaan",
                "Ini ada movil nyangkut didepan",
                "Jalan kenangan indah",
                "12/11/2023",
                true
            ),
            Report(
                4,
                "Kebakaran",
                "Pemadam Kebakaran",
                "Kecelakaan",
                "Ini ada movil nyangkut didepan",
                "Jalan kenangan indah",
                "12/11/2023",
                true
            )
        )
    }
}