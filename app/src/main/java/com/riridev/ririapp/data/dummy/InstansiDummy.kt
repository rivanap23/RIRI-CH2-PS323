package com.riridev.ririapp.data.dummy

import com.riridev.ririapp.data.model.Instansi

class InstansiDummy {
    companion object {
        val dataInstansi = listOf(
            Instansi(1, "BPBD", "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/cropped-BPBD-1.png?raw=true", contact = 112),
            Instansi(2, "Pemadam Kebakaran", "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo-pemadam.png?raw=true", contact = 112),
            Instansi(3, "Polres", "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/Lambang_Polri%201.png?raw=true", contact = 112),
            Instansi(4, "Dinas Kesehatan", "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo_kemenkes.png?raw=true", contact = 112),
            Instansi(5, "Ambulans", "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo_ambulans.png?raw=true", contact = 112),
        )
    }
}