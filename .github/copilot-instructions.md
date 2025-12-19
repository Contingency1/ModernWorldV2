1. 아키텍처 원칙 (Architecture Principles)

    계층 분리: 도메인 계층(Domain)과 인프라 계층(Infrastructure)을 엄격히 분리한다.

    의존성 역전(DIP): 도메인 계층은 인프라 계층의 구현체에 의존하지 않는다. 항상 port 인터페이스를 통해 소통한다.

    객체 구분: DB와 매핑되는 객체는 *JPAEntity로 명명하고, 비즈니스 로직에서 사용하는 객체는 순수 도메인 객체(예: User)를 사용한다.

2. JPA 및 데이터 저장 규칙 (Safe Save Pattern)

    단순 save 금지: 기존 데이터를 수정할 때 엔티티를 새로 생성해서 repository.save()에 집어넣는 방식을 금지한다.

    조회 후 변경 감지(Dirty Checking): 수정을 위해서는 반드시 findById 혹은 비관적 락(LockModeType.PESSIMISTIC_WRITE)으로 영속 상태의 엔티티를 조회한 후, 도메인 객체의 변경 사항을 엔티티에 반영하는 방식을 사용한다.

    ID 기반 분기: Repository 구현체(*RepositoryImpl)의 save 메서드는 항상 id == null 인지 확인하여 신규 저장과 수정을 명확히 분리한다.

3. MapStruct 매퍼 설정 (Mapping Rules)

    컬렉션 보호: @MappingTarget을 사용하는 업데이트 매퍼 메서드에서, 자식 테이블(Collection) 필드는 반드시 ignore = true 처리하여 JPA의 orphanRemoval로 인한 원치 않는 삭제를 방지한다.

    불변 필드 보호: 기본키(no), 외래키 참조 필드(user, comment 등)는 업데이트 시 매핑 대상에서 제외(ignore = true)한다.

4. QueryDSL 및 벌크 연산 (Bulk Operations)

    영속성 컨텍스트 동기화: QueryDSL의 .delete() 또는 .update()와 같은 벌크 연산을 수행한 직후에는 반드시 entityManager.clear()를 호출하여 영속성 컨텍스트와 DB 간의 데이터 불일치(유령 데이터) 문제를 방지한다.

    권한 포함 삭제: 삭제 시에는 단순히 ID로만 삭제하지 않고, where 절에 요청자의 ID(userNo)를 포함하여 권한 검증을 쿼리 수준에서 처리한다.

5. 비즈니스 로직 및 이벤트 (Business Logic & Events)

    관심사 분리: 알림 생성, 업적 카운트 증가와 같은 부가 로직은 ApplicationEventPublisher를 통한 이벤트 기반으로 처리한다.

    트랜잭션 관리: 트랜잭션은 Service 레이어에서 시작한다. 읽기 전용 작업은 @Transactional(readOnly = true)를 명시한다.

    도메인 예외: 도메인 모델 내에서 발생하는 예외는 IllegalStateException 등을 던지고, Service 계층에서 이를 캐치하여 정의된 BusinessException으로 변환한다.

6. 자주 실수하는 내용 (Common Pitfalls)

    @OneToMany 주의: 부모 엔티티에 리스트 필드를 추가할 때는 정말로 생명주기를 같이 하는지(Composition 관계인지) 확인하고, 그렇지 않다면 리스트 필드 없이 해당 자식 Repository에서 직접 조회한다.

    Redis 구현: RefreshTokenRepository 구현 시 Redis의 조회(get) 및 삭제(delete) 로직이 누락되지 않았는지 항상 확인한다.
