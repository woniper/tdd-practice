2. 문자열 문제 
    * 참고 : https://github.com/madvirus/codingtest-tdd
    문자열에서 원하는 단어가 있는지 확인해서,
    특정 단어가 있을 경우 그 단어를 강조하기 위해
    단어의 앞뒤에 중괄호{}를 해서 출력한다.
    단, 단어의 앞이나 뒤에 숫자/밑줄(_)/알파벳이 나오면
    다른 단어라고 판단하고 출력하지 않는다.
    
    예를 들어, 원하는 단어가 flex 라고 할 경우, 입력과 출력은 아래와 같다.
    
    [입력 --> 출력]
    flex?  --> {flex}?
    (flex) --> ({flex})
    reflex --> 없음
    yes, flex1 --> 없음
    no, flexible --> 없음
    no a flex --> no a {flex}