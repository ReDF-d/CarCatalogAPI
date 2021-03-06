CarCatalogAPI - сервис справочника автомобилей, реализация REST API.

Реализован при использовании Java 11, Spring Boot, Hibernate, GraphQL, postgreSQL

///////////////////////////

Сущности:

Car - сущность автомобиля, имеет следующие поля:

id - идентификационный номер автомобиля(int)

brand - производитель автомобиля(string)

model - модель автомобиля(string)

color - цвет автомобиля(string)

yearOfIssue - год выпуска автомобиля(int)

registrationNumber - регистрационный номер автомобиля(уникален)(string)

createdAt - дата и время создания записи(timestamp)

///////////////////////////

Конечные точки:

localhost:8080

GET /cars/ - получение JSON, содержащего все имеющиеся в базе автомобили.


GET /cars/{id} - получение JSON с полями запрашиваемого автомобиля по его идентификационному номеру, 
{id} - идентификационный номер автомобиля, 
возвращает 404 в случае отсутствия в базе соответствующей записи, 
возвращает 400 в случае некорректного параметра запроса.


GET /cars?brand={}&model={}&color={}&registrationNumber={}&yearFrom={}&yearTo={}/ - JSON, содержащий автомобили, соответствующие параметрам запроса, 
каждый из параметров является необязательным, 
возвращает 400 в случае, если любой из параметров запроса некорректен.


POST /cars/add/ - выполняет добавление записи в базу данных. 
Принимаемые параметры: brand, model, color, yearOfIssue, registrationNumber. 
Возвращает 201 при успешном добавлении записи, 
возвращает 400, если один из параметров некорректен, 
возвращает 409, если в базе уже есть автомобиль с указанным регистрационным номером.


DELETE /cars/{id} - удаляет запись из базы по его идентификационному номеру,
{id} - идентификационный номер автомобиля, 
возвращает 404 в случае отсутствия в базе соответствующей записи, 
возвращает 400 в случае некорректного параметра запроса,
возвращает 204 в случае успешного выполнения запроса


GET /stats/ - возращает JSON со статистикой по базе данных(количество записей, дата добавления последней и самой старой записей)
///////////////////////////

GraphQL

/graphql/ - адрес направления запросов GraphQL


Запросы:

cars - получение всех автомобилей

car(id: Int) - получение автомобиля по его идентификационному номеру

carsByParams(brand : String
    	
	model : String
    	
	registrationNumber : String
    	
	color : String
    	
	yearFrom : Int
	
	yearTo : Int) - получение автомобилей, соответствующих параметрам запроса
	
///////////////////////////

Мутации:

newCar(brand : String
	
	model : String
	
	registrationNumber : String
	
	color : String
	
	yearOfIssue : Int) - выполняет добавление записи в базу данных. 

deleteCar(id : Int) - удаляет запись из базы данных

///////////////////////////
