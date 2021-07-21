# FamilyTree
 * находится в основном модуле
## FamilyController имеются 4 метода, которые соответственно обращаются через FamilyPersonService
к методам PersonFamilyEntityRepository

### метод Get http://localhost:8090/avahidov/family-tree/10001000
возвращает дерево для клиента 10001000 с признаком live = true
```json
{
  "passport": 10001000,
  "name": "Ivanov",
  "secondName": "Ivanovich",
  "parent": [
    {
      "passport": 10001001,
      "name": "Lev",
      "secondName": "Dymich",
      "parent": [
        {
          "passport": 10001011,
          "name": "Alrksandra",
          "secondName": "Timurovna"
        }
      ]
    },
    {
      "passport": 20001001,
      "name": "Lena",
      "secondName": "Aleksandrovna"
    }
  ],
  "sisterBrother": [
    {
      "passport": 20001000,
      "name": "Denis",
      "secondName": "Ivanovich"
    }
  ],
  "children": [
    {
      "passport": 10011000,
      "name": "Aleksey",
      "secondName": "Ivanovich",
      "children": [
        {
          "passport": 10111000,
          "name": "Anton",
          "secondName": "Alekseych"
        }
      ]
    },
    {
      "passport": 10021000,
      "name": "Andrey",
      "secondName": "Ivanovich"
    }
  ]
}
```
### метод POST http://localhost:8090/avahidov/family-tree/add-person
#### принимает в теле FamilyPersonDto для добавления объекта в БД

### метод PUT http://localhost:8090/avahidov/family-tree/update-person
#### принимает в теле FamilyPersonDto для обновления объекта в БД

### метод DELETE http://localhost:8090/avahidov/family-tree/delete-person
#### принимает в теле Long passport для удаления объекта в БД