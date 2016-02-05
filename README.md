
_gardengnome_ provides a simple RSB service for querying a MongoDB database containing user profiles. It requires a running `mongod` instance.

Starting it with `factoryreset` as argument will reset the underlying database with the data specified in `src/main/resources/data`.

Currently, it also provides a REST service:

## Show basic information about the database

```
curl -X GET "http://52.29.87.138:1555/kognihome/userprofiles/status"
```

## Querying static information

```
curl -X GET "http://52.29.87.138:1555/kognihome/userprofiles/query" -d "{ \"uid\": \"katharinabecker\", \"ask\": \"<attribute>\" }"
```

Where `<attribute>` currently covers:

* `name`, `firstname`, `lastname`
* `gender`
* `height`
* `birthdate`, `age` , `hasbirthday`

## Adding documents to the database

```
curl -X POST "http://52.29.87.138:1555/kognihome/userprofiles/write" -d @src/test/document.json
```

Where `document.json` has, for example, the following structure:

```
{
  "coll": "test" ,
  "creator": "tester" ,

  "doc": {
           "uid": "alexanderbecker" ,
           "date" : "2015-12-15" ,
           ...
   }
}
```

## Retrieving documents from the database

```
curl -X GET "http://52.29.87.138:1555/kognihome/userprofiles/retrieve" -d "{ \"coll\": \"activitydata\", \"find\": { \"uid\": \"alexanderbecker\", \"date\": \"2015-12-15\" } }"
```
