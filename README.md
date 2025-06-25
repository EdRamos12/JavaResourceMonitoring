# 🖥️ Monitor de Recursos do Sistema (Java + OSHI + Singleton + Swing)

Este projeto é uma aplicação Java que monitora em tempo real o uso de **CPU**, **Memória RAM** e **Disco**, utilizando a biblioteca [OSHI](https://github.com/oshi/oshi). A arquitetura foi projetada com o padrão **Singleton**, garantindo que a coleta de dados do sistema ocorra em um único ponto centralizado. A interface gráfica foi feita com **Java Swing**, permitindo uma visualização simples e eficaz dos dados.

---

## Demonstração

> Interface principal com leitura automática dos dados do sistema em tempo real:

![Screenshot](https://github.com/EdRamos12/JavaResourceMonitoring/blob/main/img/Screenshot.png)

> Janelas específicas para CPU, RAM e Disco:

- Janela de CPU com uso por núcleo
- Janela de RAM mostrando uso atual
- Janela de Disco listando montagens e uso

---

## Funcionalidades

- ✅ Coleta automática de informações do sistema com intervalo configurável
- ✅ Leitura de CPU total e por núcleo
- ✅ Leitura de uso de memória RAM
- ✅ Leitura de uso de disco por partição
- ✅ Interface gráfica com atualização em tempo real
- ✅ Janelas separadas para cada recurso (CPU, RAM, Disco)
- ✅ Design orientado a objetos com padrão **Singleton**
- ✅ Exibição centralizada das janelas ao abrir

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia      | Descrição                                 |
|-----------------|---------------------------------------------|
| Java            | Linguagem principal                         |
| OSHI            | Biblioteca de coleta de dados do sistema    |
| Swing (JFrame)  | Interface gráfica                           |
| Padrão Singleton| Para gerenciar um único ponto de coleta     |
| Threads         | Para atualização assíncrona e contínua      |

---

# Estrutura do Projeto

![UML](https://github.com/EdRamos12/JavaResourceMonitoring/blob/main/img/UML.png)

```
projeto-monitor
├── javaapplication2/
│   └── JavaApplication2.java
├── resource/
│   └── ResourceMonitor.java
├── separate/
│   ├── CPUWindow.java
│   ├── RAMWindow.java
│   └── DiskWindow.java
└── README.md
```