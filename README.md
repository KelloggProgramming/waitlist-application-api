# Waitlist API

## Docker image

### Environment Variables

| variable                   | required | options                   |
|----------------------------|----------|---------------------------|
| SPRING_PROFILES_ACTIVE     | false    | _empty_ or stage          |
| SPRING_DATASOURCE_URL      | true     | MariaDb connection string |
| SPRING_DATASOURCE_USERNAME | true     | MariaDb Username          |
| SPRING_DATASOURCE_PASSWORD | true     | MariaDb Password          |