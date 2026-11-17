# Round-Robin Load Balancing at API Gateway

Modules
- service-registry : Eureka server (port 8761)
- driver-service   : run 3 instances on random ports (server.port=0), registered as DRIVER-SERVICE
- api-gateway      : Spring Cloud Gateway (port 8080), /driver/** -> lb://DRIVER-SERVICE

Import in STS: File > Import > Maven > Existing Maven Projects > select the extracted folder
(Java 17+, internet needed for the first build).

Run order
1. ServiceRegistryApplication
2. DriverServiceApplication - start it 3 times
3. ApiGatewayApplication
4. http://localhost:8761 -> DRIVER-SERVICE should show 3 instances
5. Open http://localhost:8080/driver repeatedly. The port in the response rotates 1 -> 2 -> 3 -> 1 ...
   Console logs of each driver-service and of the gateway show the same.
