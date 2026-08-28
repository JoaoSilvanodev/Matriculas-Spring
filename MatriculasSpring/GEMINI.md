# Memória e Contexto do Projeto: Matriculas-Spring

> [!IMPORTANT]
> **Instrução para o Agente (Antigravity):**
> 1. Ao iniciar qualquer nova sessão neste projeto, consulte este arquivo (`GEMINI.md`) para entender o histórico, as decisões tomadas e o estado atual do desenvolvimento.
> 2. Sempre que concluirmos uma nova funcionalidade, alteração importante ou decisão de arquitetura, **atualize este arquivo** adicionando os novos tópicos no histórico abaixo.

---

## 📋 Visão Geral do Projeto
- **Nome:** MatriculasSpring (`com.clogs.MatriculasSpring`)
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

### 2026-08-21 & 2026-08-22
- **Definição Metodológica:** Estabelecido formato de mentoria focado em aprendizado ativo (sem geração automática de código).
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

### 2026-08-24
- **Autenticação JWT & Filtro de Segurança:**
  - Implementado `TokenService` com a biblioteca `com.auth0:java-jwt` para geração e validação de tokens JWT usando secret configurada em `application.properties`.
  - Implementado `SecurityFilter` estendendo `OncePerRequestFilter` para interceptar cabeçalho `Authorization: Bearer <token>` e definir `SecurityContextHolder`.
  - Atualizado `SecurityConfig` integrando o `SecurityFilter` antes do `UsernamePasswordAuthenticationFilter` e definindo sessão `STATELESS`.
  - Modelagem do Domínio: adicionadas entidades `User`, `Student`, `Professor`, `Admin`, `Subject`, `Enrollment` e enums (`UserRole`, `EnrollmentStatus`).


