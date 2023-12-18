# Admin Report API
Dokumentasi untuk admin app.

## Admin Process the Report

Endpoint : PUT /:reportId/report/diproses

Path Variables
- reportId

Request Body :

Response Body Success :

```json
{
    "message": "Laporan sedang diproses.",
}
```


## Admin Reject the Report

Endpoint : PUT /:reportId/report/ditolak

Path Variables
- reportId

Request Body :

Response Body Success :

```json
{
    "message": "Laporan ditolak.",
}
```


## Admin Finish the Report

Endpoint : PUT /:reportId/report/ditolak

Path Variables
- reportId

Request Body :

Response Body Success :

```json
{
    "message": "Laporan selesai.",
}
```