## Mini-Projeto Sockets

A aplicação permite criar um chat, em grupo, para usuários em uma rede local. Não há limites para o número de participantes do grupo. No chat, a qualquer momento um usuário participante pode enviar/receber mensagens. A mensagem poderá ser enviada ao chat (permitindo que todos os participantes a vejam) ou, reservadamente, a um usuário específico que esteja online. Cada mensagem aparece, para cada usuário, no seguinte formato:

                            <IP>:<PORTA>/~<nome_usuario> : <mensagem> <hora-data>

**Onde:**<br>
&lt;IP> : Número do IP de onde originou-se a mensagem.<br>
&lt;PORTA> Número da porta de onde originou-se a mensagem, do IP descrito acima.<br> 
&lt;nome_usuario>: nome do usuário.<br>
&lt;mensagem> :mensagem recebida.<br>
&lt;hora-data> : hora e data da mensagem recebida, de acordo com o horário do servidor.<br>

      Exemplo de mensagem recebida no grupo ou no reservado:

                         192.168.0.123:67890/~ana: Alguma novidade do mini-projeto? 14h31 14/06/2016

Foram desenvolvidas duas aplicações, seguindo a arquitetura cliente-servidor. Uma aplicação servidora para gerenciar os usuários conectados ao chat e as mensagens postadas nele, interpretando os comandos recebidos pelos usuários do chat. Outra aplicação cliente (usada por cada participante do grupo), em máquinas distintas, apenas para se conectar, enviar comandos pro servidor e desconectar do chat. 
Funcionalidades são executadas/solicitadas através de linhas de comando, no lado cliente (não haverá menu), que serão interpretadas pelo servidor. Funcionalidades:

| Funcionalidade | Comando | Restrição |
|:-----------|------------:|:------------:|
| Sair do grupo       |        bye |     __     |
| Enviar mensagem ao grupo     |      send -all &lt;mensagem>|    __    |
| Enviar mensagem reservada|         send -user &lt;nome_usuario> <mensagem> |     Notificar o usuário caso <nome_usuario> não exista.     |
|  Visualizar participantes        |          list |      __      |
| Renomear usuário       |       rename &lt;novo_nome> |    Notificar o usuário: Renomeado com sucesso ou Nome de usuário já em uso.   |

**Observações:**

* Se o usuário informar um comando diferente,o servidor deverá informar que tal comando é inválido;<br>
* Toda a comunicação entre cliente-servidor deverá ser através do protocolo TCP;<br>
* Só existirá apenas um chat.
