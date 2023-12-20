package com.riridev.ririapp.data.dummy

import com.riridev.ririapp.R
import com.riridev.ririapp.data.model.Instansi

class InstansiDummy {
    companion object {
        val dataInstansi = listOf(
            Instansi(
                1,
                "BPBD",
                "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/cropped-BPBD-1.png?raw=true",
                contact = 112,
                imageRes = R.drawable.bpbd,
                desc = "Badan Penanggulangan Bencana Daerah yang disebut juga dengan BPBD adalah perangkat daerah kabupaten yang dibentuk dalam rangka melaksanakan tugas dan fungsi penaggulangan bencana daerah. \n" +
                        "\n" +
                        "Badan Penanggulangan Bencana Daerah (BPBD) merupakan lembaga penanggulangan bencana yang berkedudukan di bawah dan bertanggung jawab kepada Gubernur. BPBD memiliki misi untuk Meningkatkan penyelenggaraan penanggulangan bencana secara terencana, terpadu, dan menyeluruh; Meningkatkan upaya penyediaan sarana dan prasarana layanan penanggulangan bencana; Memberikan pelayanan yang profesional dan tangguh dalam penanggulangan bencana baik pada pra, saat, dan pasca; Melaksanakan pemberdayaan dan peningkatan peran aktif dan partisipasi masyarakat dalam penanganan bencana.\n",
                category = "Penaggulangan Bencana"
            ),
            Instansi(
                2,
                "Pemadam Kebakaran",
                "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo-pemadam.png?raw=true",
                contact = 113,
                imageRes = R.drawable.pemadam,
                desc = "Dinas Pemadam Kebakaran dan Penyelamatan mempunyai tugas melaksanakan Urusan Pemerintahan dengan membantu Walikota di bidang Ketenteraman dan Ketertiban Umum serta Perlindungan Masyarakat sub Kebakaran.\n" +
                        "\n" +
                        "Misi Damkar adalah sebagai berikut: Meningkatkan profesionalisme aparatur Dinas Pemadam Kebakaran yang berdedikasi tinggi, peduli serta antisipasif; Memberikan pelayanan prima dalam bidang pencegahan, penanggulangan Kebakaran serta penyelamatannya; Meningkatkan ketahanan lingkungan di bidang pencegahan dan penanggulangan kebakaran kepada masyarakat.\n",
                category = "Kebakaran dan Situasi Darurat"
            ),
            Instansi(
                3,
                "Polres",
                "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/Lambang_Polri%201.png?raw=true",
                contact = 110,
                imageRes = R.drawable.polri,
                desc = "Kepolisian Resor (Polres) adalah struktur komando Kepolisian Republik Indonesia di tingkat kabupaten/kota. \n" +
                        "\n" +
                        "Polres memiliki misi meliputi perlindungan, pengayoman, dan pelayanan agar masyarakat merasa aman. Meningkatkan kesadaran hukum dengan bimbingan preemtif dan preventif. Menegakkan hukum secara profesional, menjunjung tinggi supremasi hukum dan hak asasi manusia. Memelihara ketertiban dengan memperhatikan norma-norma setempat. Mengelola sumber daya manusia secara profesional, meningkatkan konsolidasi, dan berkomitmen melayani dengan hati, tulus, ikhlas, serta simpatik untuk mencapai kesejahteraan masyarakat.\n",
                category = "Kriminalitas"
            ),
            Instansi(
                4,
                "Dinas Kesehatan",
                "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo_kemenkes.png?raw=true",
                contact = 119,
                imageRes = R.drawable.kemenkes,
                desc = "Kementerian Kesehatan bertugas menyelenggarakan urusan pemerintahan di bidang kesehatan untuk mendukung Presiden dalam tugas pemerintahan. \n" +
                        "\n" +
                        "Sesuai PMK No. 5 Tahun 2022, Kementerian Kesehatan memiliki fungsi-fungsi tertentu. Misi utamanya meliputi penurunan angka kematian ibu dan bayi, penurunan angka stunting pada balita, perbaikan pengelolaan Jaminan Kesehatan Nasional, serta peningkatan kemandirian dan penggunaan produk farmasi serta alat kesehatan dalam negeri.\n",
                category = "Kesehatan dan Gawat Darurat"
            ),
            Instansi(
                5,
                "Ambulans",
                "https://github.com/Boy-Brahmanda03/Riri-Asset/blob/main/logo_ambulans.png?raw=true",
                contact = 119,
                imageRes = R.drawable.ambulans,
                desc = "Ambulans, sebagai sarana evakuasi medis, merupakan bagian integral dari pelayanan kesehatan dan Sistem Penanggulangan Gawat Darurat Terpadu (SPGDT). Pelayanan ambulans harus mematuhi standar pelayanan dan persyaratan keamanan. Ambulans gawat darurat darat digunakan untuk menangani dan mengangkut pasien dengan kondisi gawat darurat atau berpotensi mengancam nyawa dari lokasi kejadian ke tempat tindakan definitive atau ke rumah sakit untuk pengobatan.",
                category = "Gawat Darurat"
            ),
        )
    }
}