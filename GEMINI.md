# Memória e Contexto do Projeto: Matriculas-Spring

> [!IMPORTANT]
> **Instrução para o Agente (Antigravity):**
> 1. Ao iniciar qualquer nova sessão neste projeto, consulte este arquivo (`GEMINI.md`) para entender o histórico, as decisões tomadas e o estado atual do desenvolvimento.
> 2. Sempre que concluirmos uma nova funcionalidade, alteração importante ou decisão de arquitetura, **atualize este arquivo** adicionando os novos tópicos no histórico abaixo.

---

## 📋 Visão Geral do Projeto
- **Nome:** MatriculasSpring (`com.clogs.matriculaspring`)
- **Tecnologia:** Java / Spring Boot
- **Propósito:** Sistema de gestão de matrículas escolares/acadêmicas e autenticação.

---

## 🎯 Diretrizes de Aprendizado e Metodologia
> [!NOTE]
> **Perfil do Usuário:** O foco principal deste projeto é estudo aprofundado, fixação de conceitos do Spring Boot e raciocínio de engenharia de software.
> **Regra de Interação:**
> - **NÃO alterar ou gerar o código pronto diretamente para o usuário.**
> - Atuar como **mentor/tutor técnico**: explicar os conceitos, papéis de cada camada, arquitetura, design patterns e fluxos.
> - Guiar o usuário com perguntas reflexivas, pseudocódigo/esboços conceituais e apontar documentações para que **ele mesmo escreva o código**.

---

## 📌 Principais Componentes Identificados
- **Entidades e Enums:** `User`, `Student`, `Professor`, `Admin`, `Subject`, `Enrollment`, `EnrollmentStatus`, `UserRole`
- **DTOs:** `StudentDto`, `LoginDto`
- **Controllers:** `AuthController` (`com.clogs.matriculaspring.controller.AuthController`)
- **Services:** `AuthService`, `TokenService` (`com.clogs.matriculaspring.service`)
- **Repositories:** `StudentRepository`, `UserRepository`, `EnrollmentRepository`
- **Config & Security:** `SecurityConfig`, `SecurityFilter`, `AdminSeeder`

---

## 📝 Diário de Tópicos e Decisões

### 2026-08-21 & 2026-08-24
- **Simplificação e Refatoração para Aprendizado Direto:**
  - Decidido simplificar a arquitetura removendo fluxos complexos de aprovação manual para focar no aprendizado essencial de Spring Boot REST API.
  - Escopo simplificado em 3 pilares:
    1. **Autenticação Simples:** Registro e Login de Usuários com JWT.
    2. **CRUD de Matérias (`Subject`):** Criar, Listar, Buscar, Atualizar e Deletar.
    3. **Matrículas (`Enrollment`):** Matricular aluno em matéria e listar matrículas.
  - **Tratamento Global de Exceções (`@RestControllerAdvice`):**
  - Implementados `ErrorResponseDto` em `dto.error` e `GlobalExceptionHandler` em `config`.
  - Interceptação centralizada de `IllegalArgumentException`, `RuntimeException` e `MethodArgumentNotValidException` retornando JSONs de erro padronizados com timestamp e status 400/500.
  - **Validação Automática de DTOs (`Bean Validation`):**
  - Adicionadas anotações de validação (`@NotBlank`, `@Email`, `@Size`) em `RegisterRequestDto`.
  - Adicionado `@Valid` nas entradas dos controllers para acionamento automático de validação HTTP.
  - **Conceitos Abordados:**
  - Java `record` vs Kotlin `data class` (imutabilidade, boilerplate reduction, DTOs).
  - Papel e responsabilidades do `AuthService` na camada de negócio para autenticação/login/registro.
  - Como o Spring/Jackson faz o binding/deserialização de JSON HTTP para DTO (`@RequestBody`).
  - Regras essenciais de negócio no `register`: verificação de unicidade (`existsByEmail`), criptografia de senha (`PasswordEncoder`) e transacionalidade (`@Transactional`).
  - Testes unitários com JUnit 5 e Mockito (`@Mock`, `@InjectMocks`, `when().thenReturn()`, `verify()`, `assertThrows()`, `ArgumentCaptor`).
  - Criptografia de senhas: `PasswordEncoder`, algoritmo BCrypt e dependências do Spring Security.
  - Configuração do Spring Security 6+ com `SecurityFilterChain` e liberação de rotas públicas (`/register`, `/login`).
  - Injeção de dependências no `AuthController` e retorno com status HTTP semânticos (`201 Created`, `400 Bad Request`).
- **Configuração de Persistência:** Criado `GEMINI.md` para carregar o contexto automaticamente em novas sessões.
- **Ambiente & Infraestrutura (Docker / PostgreSQL / pgAdmin):**
  - Resolução de conflito de portas (5432 ocupada por processos ou `docker-proxy` órfãos).
  - Diferenciação conceitual entre *Master Password* do pgAdmin (cofre de credenciais local) e credenciais do banco PostgreSQL.
  - Sincronização e persistência de credenciais no container Docker (`postgres-matriculas`) via `ALTER ROLE` e `docker-compose.yml`.
  - **Reset do pgAdmin 4 (Flatpak):** Efetuado o reset da *Master Password* esquecida através do backup do arquivo `~/.var/app/org.pgadmin.pgadmin4/config/pgadmin4.db`.
  - **Confirmação de Conexão do Banco:** Validado que o banco `matriculas_db` está ativo no container `postgres-matriculas` (Porta: `5432`, Usuário: `postgres`, Senha: `postgres`).

- **Reset e Limpeza do Projeto (Clean Slate):**
  - Todo o código de entidades, segurança, serviços e repositórios anteriores foi limpo a pedido do usuário para eliminar complexidade desnecessária.
  - Mantida apenas a estrutura de pacotes base limpa (`config`, `controller`, `dto`, `model`, `repository`, `service`).

### 2026-08-25
- **Conclusão do Módulo CRUD de Matérias (`Subject`):**
  - Implementados `Subject` (Entity), `SubjectRequestDto` e `SubjectResponseDto` (Records), `SubjectRepository` (com `existsByCode`), `SubjectService` (CRUD completo) e `SubjectController` (rotas REST sob `/subjects`).
- **Conclusão do Módulo de Autenticação Simples (`User`):**
  - Entidade `User` simplificada (sem hierarquia nem atrelamento ao `UserDetails`).
  - DTOs limpos em `dto.auth`: `RegisterRequestDto`, `LoginRequestDto` e `UserResponseDto`.
  - `UserRepository` com `existsByEmail` e `findByEmail`.
  - `AuthService` em `service.auth` com hash de senha via `PasswordEncoder` (BCrypt).
  - `AuthController` em `controller.auth` mapeado sob `/auth` (`/auth/register` e `/auth/login`).
- **Conclusão do Módulo de Matrículas (`Enrollment`):**
  - Implementados `Enrollment` (Entity com `@ManyToOne` para `User` e `Subject`), `EnrollmentRequestDto` e `EnrollmentResponseDto` (Records em `dto.enrollment`), `EnrollmentRepository` (com `findByUserIdAndSubjectId`), `EnrollmentService` (validação de existência de usuário/matéria e prevenção de duplicidade) e `EnrollmentController` (`POST`, `GET`, `DELETE` sob `/enrollments`).
  - Atualizado `SecurityConfig` liberando `/enrollments/**`.
  - **Validação com Swagger UI:** Testados com sucesso todos os endpoints de Autenticação (`/auth`), Matérias (`/subjects`) e Matrículas (`/enrollments`) via Swagger UI.

### 2026-08-26 & 2026-08-27
- **Conclusão do Módulo de Segurança JWT Stateless:**
  - Implementado `TokenService` (geração e validação de tokens JWT usando `com.auth0:java-jwt` com algoritmo HMAC256 e expiração configurada).
  - Implementado `SecurityFilter` (`OncePerRequestFilter`) para interceptação do header `Authorization: Bearer <token>`, extração do e-mail e autenticação no `SecurityContextHolder`.
  - Atualizado `AuthService` e `AuthController` no endpoint `POST /auth/login` retornando a String do Token JWT diretamente.
  - Atualizado `SecurityConfig` configurado com `SessionCreationPolicy.STATELESS`, `.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)` e restrição das rotas `/subjects/**` e `/enrollments/**` exigindo autenticação por Token.
  - Configurado `@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")` habilitando o botão "Authorize 🔓" no Swagger UI.
  - **Validação de ponta a ponta:** Testado com sucesso no Swagger UI (obtenção do Token no login, autorização Bearer e acesso protegido aos endpoints).
