# Warning module

Run `src/main/resources/warning-module.sql` once on `huiyan_db` before starting the upgraded service.

## Threshold management

Supported `warningType` values are `WATER_LEVEL` and `RAINFALL`.

```http
GET /api/warning/thresholds
```

```http
POST /api/warning/thresholds
Content-Type: application/json

{
  "name": "Water danger",
  "warningType": "WATER_LEVEL",
  "warningLevel": 2,
  "threshold": 25,
  "unit": "cm",
  "enabled": true,
  "pushChannels": "WEB,SMS",
  "updatedBy": "admin"
}
```

Use `PUT /api/warning/thresholds/{id}` to update a rule and
`DELETE /api/warning/thresholds/{id}` to delete it.

## Warning workflow

The MQTT listener stores monitoring data first, then evaluates both water level
and rainfall. A matching warning starts in `PENDING` state.

```http
GET /api/warning/list?status=PENDING
GET /api/warning/{id}
```

Record a delivery attempt and move a pending warning to `PROCESSING`:

```http
POST /api/warning/{id}/push
Content-Type: application/json

{"channels":"WEB,SMS"}
```

Archive a handled warning:

```http
PUT /api/warning/{id}/handle
Content-Type: application/json

{"handledBy":"operator","remark":"Road closed and drainage started"}
```

The push endpoint records delivery state and channels. Actual SMS, WeChat, or
DingTalk delivery requires the corresponding provider credentials and adapter.
