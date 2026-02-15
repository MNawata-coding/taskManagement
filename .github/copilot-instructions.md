# Copilot Instructions for Todo Application

## Project Overview
A full-stack todo application portfolio project demonstrating modern LTS Java and React development practices. Backend uses Spring Boot 4 with Java 21; frontend uses React 19 with Vite and TypeScript.

**Tech Stack:**
- **Backend:** Spring Boot 4.0.0, Java 21 (LTS), JPA with Flyway migrations
- **Frontend:** React 19, TypeScript, Vite with Rolldown, Vite >=7.2.5
- **Database:** MySQL 8.x (localhost:3306/taskmanage)
- **Build Tools:** Gradle 8.14+ (backend), npm (frontend)

## Architecture Decisions

### Core Architecture
- **Service Pattern:** `CreateTaskServiceImple` (note: intentional misspelling "Imple" used throughout) implements `CreateTaskService` interface
- **Data Flow:** REST API → Controller → Service → Repository (JPA) → MySQL
- **Controller Endpoint:** `@PostMapping("/test/save")` for task creation (intentional test naming)
- **Database Migrations:** Flyway with V1__create_taskTable.sql; enabled in application.properties

### Key Technologies & Rationale (From ADRs)
1. **Java 21 + Spring Boot 4** (ADR 0001): Demonstrates LTS Java capability; enables virtual thread learning; Java 17+ required by Boot 4
2. **Lombok:** Reduces boilerplate with `@Builder`, `@Getter`, `@NoArgsConstructor` (java 21 with annotation processors)
3. **Flyway:** Database versioning via classpath migrations + filesystem fallback (`/opt/migration`)
4. **GuavaLib:** Already included but minimal usage; available for utility patterns

## Development Workflows

### Backend Build & Run
```bash
# From backend/ directory
./gradlew bootRun           # Start Spring Boot app (port 8080 default)
./gradlew build             # Full build
./gradlew test              # Run JUnit5 tests
./gradlew bootJar           # Create executable JAR
```

### Frontend Build & Run
```bash
# From frontend/vite-project/ directory
npm run dev                 # Dev server (HMR enabled)
npm run build              # Build with tsc type check + Vite bundle
npm run lint               # ESLint checks
npm run preview            # Preview production build
```

### Database
- **URL:** `jdbc:mysql://localhost:3306/taskmanage?useSSL=false&serverTimezone=UTC`
- **Credentials:** username=`root`, password=`password`
- **Flyway Auto-Migration:** Enabled on boot; looks in `classpath:db/migration/` and filesystem `/opt/migration/`
- **New Migrations:** Create `VN__description.sql` in `src/main/resources/db/migration/`

## Code Patterns & Conventions

### Entity Definition Pattern
```java
@Entity @Getter @NoArgsConstructor @Table(name="task")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull
    private String taskName;
    private LocalDateTime createDateTime;
    
    @Builder
    private Task(String taskName) {
        this.taskName = taskName;
        this.createDateTime = LocalDateTime.now();  // Auto-populate on creation
    }
}
```

### Service Implementation Pattern
- Interface: `CreateTaskService` (in service/ package)
- Implementation: `CreateTaskServiceImple` (implements interface, uses `@Service`, `@RequiredArgsConstructor`)
- Method: Maps request DTOs to entities, uses `Task.builder()`, delegates persistence to `TaskRepository`

### DTO Pattern
- Request DTOs in `src/main/java/com/example/demo/dto/requestDto/` (e.g., `TaskRequestDto.java`)
- Use records or simple classes; request DTOs are immutable input contracts

### Repository Pattern
- Extend `JpaRepository<Task, Integer>` for basic CRUD
- Custom queries commented as "necessary as needed"; rely on Spring Data defaults first
- No explicit implementation needed; Spring Data generates proxies

### Controller Pattern
- `@RestController` with `@RequestMapping("/test")` base path
- `@PostMapping` and `@GetMapping` for REST endpoints
- Inject service via `@RequiredArgsConstructor` constructor injection
- DTOs in `@RequestBody` for POST requests

## Frontend Conventions

### React Component Pattern
- Functional components with TypeScript (`.tsx`)
- React 19 with Hooks (`useState`, etc.)
- ESLint configured with React refresh plugin
- Vite/Rolldown as bundler (not Webpack)

### TypeScript Configuration
- `tsconfig.json` (app code) and `tsconfig.node.json` (build tooling)
- Strict mode recommended for new components

## Critical Integration Points

### Backend-Frontend Communication
- **CORS:** May need configuration in Spring Boot (not yet visible in code)
- **API Base:** Frontend will likely call `http://localhost:8080/test/save` in dev
- **Content-Type:** `application/json` for REST payloads

### Database Initialization
- Flyway runs automatically on `bootRun`; ensure MySQL is running before starting backend
- If migrations fail, check `/opt/migration/` filesystem path permissions

## Project-Specific Conventions

1. **Naming Quirks:**
   - Service implementation uses `*Imple` suffix (not standard `*Impl`); preserve this convention for consistency
   - Test endpoint path `/test` is intentional for portfolio; do not assume production-ready naming

2. **Logging:**
   - Uses `System.out.println()` for now; consider replacing with `org.slf4j.Logger` for production improvements

3. **Validation:**
   - Entity uses `@NotNull` (Jakarta validation); controller DTOs should mirror this approach

4. **Java 21 Features:**
   - Virtual threads are available but not yet implemented; can be enabled via Spring Boot properties if needed
   - Records are available for simple data classes (e.g., DTOs)

## Common Tasks

### Adding a New Task Endpoint
1. Create `NewFeatureRequestDto` in `dto/requestDto/`
2. Add method to `CreateTaskService` interface
3. Implement in `CreateTaskServiceImple`
4. Add `@PostMapping` or `@GetMapping` in `TaskController`
5. Inject service and invoke

### Adding Database Columns
1. Create migration `VN__add_column.sql` in `src/main/resources/db/migration/`
2. Update `Task` entity with new field + getter
3. Update `TaskRequestDto` if column accepts input

### Frontend Integration
1. Create component in `src/component/` (e.g., `taskSave.tsx` already exists)
2. Use `fetch()` or axios to call backend `/test/*` endpoints
3. Handle JSON response in component state

## Testing
- **Backend:** JUnit 5 with AssertJ; tests run via `./gradlew test`
- **Frontend:** ESLint configured; no test framework configured yet (recommend Jest/Vitest for new tests)

## External Resources
- [Spring Boot 4 Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [JPA/Hibernate Guide](https://hibernate.org/orm/documentation/)
- [Flyway Migration Docs](https://flywaydb.org/documentation/)
- [Vite Guide](https://vite.dev/guide/)
- [React 19 Docs](https://react.dev/)

---
**Last Updated:** 2026-01-28 | **Version:** 1.0
