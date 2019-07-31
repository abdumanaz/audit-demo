# audit-trail-demo

Audit trails are an essential security element associated with business transactions. It is a cross-cutting concern regardless of the business layer. This project provides basic Audit configuration for a java based application. The solution is implemented using Spring AOP. Spring AOP provides Pointcuts and advices, providing an easy yet efficient system to handle the cross cutting concerns.


## Configuration
`AuditTrailConfig` is the configuration class.
 
Every service class, whose methods are to be audited must be annotated with `@AuditableService` which takes in a parameter - `module` specifying the module of the service class.

All the public functions inside the annotated class will be audited and their parameters are persisted, provided the parameter class is annotated with `@AuditableBean`.

If a function is not to be included in the trail, annotate the function with `@ExcludeFromAudit`.

Audit Trail Entries are persisted or retrieved by the implementations of `AuditTrailConfigService` interface, which has two methods  `writeAuditTrail` and `getAuditTrail`.


## Audit-trail table
This project uses Oracle DB to persist the audit trail. Table DDL is in audit_trail.sql

## Security
Endpoints are protected using `SecurityFilter`, a basic web Filter class which checks the request for an Authorization header. The Authorization header must be of the following format:

`Authorization: Bearer ADMIN/NORMAL`  depending on the user role.

## Current API's
1. /product/add

    Requires product details in request body.
  
2. /product/view/{id}

3. /audit/get

    Requires requested audit details in request body
  
`ProductDAO`class is currently audited. `Product` bean is annotated with `@AuditableBean` and is persisted to DB.


Sample requests are attached in datalkz.postman_collection.json which can be imported to Postman App. 
API Doc : API Doc.docx

## Current Approach and Further Enhancements

`aroundAuditableMethods` in `AuditTrailConfig` class is the focal point of Audit trail configuration. It is executed for every auditable method.

If the auditable method, doesnot throw an exception, audit trailing is done.

Further enhancements can be done to the solution:

1. Include a primary key reference in the audit trail table for the bean.
2. Common exception logic.


