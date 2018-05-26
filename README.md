# MicroBlog

## Mini-tutorial para deixar a árvore do git limpa

1. Quando for começar uma issue, criar uma branch nova a partir da develop
2. Fazer as alterações e comitá-las
3. Quando terminar a issue, caso a develop tenha sido alterada, é interessante dar um rebase (que joga todos os seus commits para ponta da develop)
4. `git fetch origin -p` pra atualizar a árvore local
5. `git rebase -p origin/develop` joga todos os commits para ponta da develop
6. `git push -f origin "nome da sua branch"` pusha suas alterações. Cuidado q é um puxa forçado, é bom checar antes de dar o comando
7. Criar um pull request no site do github
8. Merjar o pull request
