# User Post API
Dokumentasi pembuatan postingan oleh user


## Create Report

Endpoint : POST /:userId/report/create-report

Path Variables
- userId

Request Body :

```form-data

- judulLaporan : "Judul laporan"
- instansi : "Instansi"
- kategoriLaporan : "Kategori laporan"
- deskripsiLaporan : "Deskripsi laporan"
- lokasi : "Lokasi laporan (Map)"
- detailLokasi "Detail lokasi laporan"
- file : image file

```

Response Body Success :

```json
{
  "data": {
    "message": "Pelaporan berhasil!",
    "reportId": "reportDocRef.id",
  }
}
```


## Get User Report History (Verifikasi)

Endpoint : GET /:userId/report/report-history/terverifikasi

Path Variables
- userId

Response Body Success :

```json
{
    "reportData" : {
        "processedReports": {
            "reportId": "doc.id",
            "judulLaporan": "reportData.judulLaporan",
            "instansi": "reportData.instansi",
            "kategoriLaporan": "reportData.kategoriLaporan",
            "deskripsiLaporan": "reportData.deskripsiLaporan",
            "lokasi": "reportData.lokasi",
            "detailLokasi": "reportData.detailLokasi",
            "buktiFoto": "reportData.buktiFoto",
            "createdAt": "reportData.createdAt",
            "status": "reportData.status",
        }
    }
}
```


## Get User Report History (Diproses)

Endpoint : GET /:userId/report/report-history/diproses

Path Variables
- userId

Response Body Success :

```json
{
    "reportData" : {
        "processedReports": {
            "reportId": "doc.id",
            "judulLaporan": "reportData.judulLaporan",
            "instansi": "reportData.instansi",
            "kategoriLaporan": "reportData.kategoriLaporan",
            "deskripsiLaporan": "reportData.deskripsiLaporan",
            "lokasi": "reportData.lokasi",
            "detailLokasi": "reportData.detailLokasi",
            "buktiFoto": "reportData.buktiFoto",
            "createdAt": "reportData.createdAt",
            "status": "reportData.status",
        }
    }
}
```


## Get User Report History (Ditolak)

Endpoint : GET /:userId/report/report-history/ditolak

Path Variables
- userId

Response Body Success :

```json
{
    "reportData" : {
        "processedReports": {
            "reportId": "doc.id",
            "judulLaporan": "reportData.judulLaporan",
            "instansi": "reportData.instansi",
            "kategoriLaporan": "reportData.kategoriLaporan",
            "deskripsiLaporan": "reportData.deskripsiLaporan",
            "lokasi": "reportData.lokasi",
            "detailLokasi": "reportData.detailLokasi",
            "buktiFoto": "reportData.buktiFoto",
            "createdAt": "reportData.createdAt",
            "status": "reportData.status",
        }
    }
}
```


## Get User Report History (Selesai)

Endpoint : GET /:userId/report/report-history/selesai

Path Variables
- userId

Response Body Success :

```json
{
    "reportData" : {
        "processedReports": {
            "reportId": "doc.id",
            "judulLaporan": "reportData.judulLaporan",
            "instansi": "reportData.instansi",
            "kategoriLaporan": "reportData.kategoriLaporan",
            "deskripsiLaporan": "reportData.deskripsiLaporan",
            "lokasi": "reportData.lokasi",
            "detailLokasi": "reportData.detailLokasi",
            "buktiFoto": "reportData.buktiFoto",
            "createdAt": "reportData.createdAt",
            "status": "reportData.status",
        }
    }
}
```
