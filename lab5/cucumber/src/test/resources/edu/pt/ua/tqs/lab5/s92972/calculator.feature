# language: pt
Funcionalidade: Aritmética básica

  Contexto: Uma calculadora
    Dada uma calculadora que acabei de ligar

  Cenário: Adição
    Quando eu adiciono 4 e 5
    Então o resultado é 9

  Cenário: Subtração
    Quando eu subtraio 2 a 7
    Então o resultado é 5

  Esquema do Cenário: Várias adições
    Quando eu adiciono <a> e <b>
    Então o resultado é <c>

  Exemplos: Digitos
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |