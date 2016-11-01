# 문자열 계산기

### 설명
문자열에 구분자와 숫자를 넘겨 숫자의 합을 계산하여 반환다.
* 구분자(seperator)는 생성자로 설정 가능
    * 구분자는 숫자가 포함될 수 없다.
    * 문자(char type)만 가능하다.
* 기본 구분자는 ","로 설정


### INPUT, OUTPUT
* 0 => 0
* 1,2 => 3
* 1,2\n3 => 6

# Retrospective
* 타입 선택을 좀 더 고민해야한다.
    * Seperator 주입 시 처음엔 String[]로했다.
    * 두번째 TDD부터는 char[]로 했다.
    * String은 문자열(str.length >= 0) 초기화가 가능하고 char는 c.length == 1 만 초기화 가능하다.
    * StringCalculator에서는 구분자가 문자(char)만 초기화 되어 있는지 유효성 체크를 해야하는데,
    * 문자 타입(char)을 사용하면 유효성 체크를 할 필요없이 문자로 제한할 수 있다.
* 3번째는 StringCalculator, Number, Seperator 클래스로 책임을 나눴다
    * 책임이 명확해졌다.
    * client (StringCalculator를 사용하는 코드)에서 StringCalculator만 접근(StringCalculator를 통해 Number, Seperator 접근) 해야될까?, Number와 Seperator까지 접근해야될까?
    * Number.sum 메소드를 추가했다.
        * Number는 문자열 중 숫자를 가지고 있는 객체 이다. 때문에 sum 메소드 뿐만 아니라 multiply 메소드도 만들기 쉬웠다. (그냥 추가만 하면 된다.)
    * 하지만 좀 더 좋은 책임 분리와, 패턴이 있을거 같다. 비교할 코드가 필요한데, 구하기 쉽지 않다.