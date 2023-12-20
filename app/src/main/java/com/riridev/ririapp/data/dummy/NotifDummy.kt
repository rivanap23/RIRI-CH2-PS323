package com.riridev.ririapp.data.dummy

import com.riridev.ririapp.data.model.NotificationModel

class NotifDummy {
    companion object {
        val listNotif = listOf(
            NotificationModel(
                1,
                "Laporan Sedang Diproses",
                "Pelaporan yang kamu telah lakukan saat ini telah memasuki tahap pemeriksaan instansi terkait",
                "10/12/2003"
            ),
            NotificationModel(
                2,
                "Laporan Ditolak!",
                "Pelaporan yang kamu telah lakukan ditolak, karena terdapat ketidaksesuaian dengan kondisi di lapangan",
                "11/12/2003"
            ),
            NotificationModel(
                3,
                "Laporan Telah Selesai",
                "Pelaporan yang kamu telah lakukan telah di selesaikan oleh instansi terkait",
                "11/12/2003"
            )
        )
    }
}