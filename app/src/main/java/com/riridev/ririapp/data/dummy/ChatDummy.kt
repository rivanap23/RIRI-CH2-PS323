package com.riridev.ririapp.data.dummy

import com.riridev.ririapp.data.model.Chat

class ChatDummy {
    companion object {
        val listChat = listOf(
            Chat(
                1,
                "Susan",
                "Saya tidak dapat mengakses akun saya. Setiap kali saya mencoba masuk, saya mendapatkan pesan kesalahan.",
                "10/10/2023"
            ),
            Chat(
                2,
                "Firly",
                "Saya mencoba mengunggah gambar, tetapi aplikasi keluar dengan sendirinya tanpa memberikan pesan kesalahan.",
                "20/10/2023"
            ),
            Chat(
                3,
                "Wawan",
                "Ya, saya punya tangkapan layar. Bagaimana cara saya mengirimkannya?",
                "12/10/2023"
            ),
            Chat(
                4,
                "Rizki",
                "Hai, saya ingin melaporkan adanya gangguan layanan. Saya tidak bisa mengakses fitur streaming video.",
                "12/11/2023"
            ),
            Chat(
                5,
                "Indra",
                "Saya belum mendengar keluhan dari pengguna lain.",
                "12/11/2023"
            ),
            Chat(
                6,
                "Bayu",
                "Hai, saya mengalami kesulitan dalam melakukan pembayaran melalui aplikasi.",
                "12/12/2023"
            ),
            Chat(
                7,
                "Yunita",
                "Saya menggunakan kartu kredit Visa, dan pesan kesalahannya hanya menyebutkan \"Transaksi Gagal.\"",
                "20/12/2023"
            )
        )
    }
}
