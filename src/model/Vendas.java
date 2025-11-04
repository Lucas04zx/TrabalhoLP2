package model;

import java.time.LocalDateTime;

public class Vendas {
    private int id;
    private LocalDateTime dataVenda;
    private double valorTotal;

    public Vendas() {
        this.dataVenda = LocalDateTime.now();
    }

    public Vendas(int id, LocalDateTime dataVenda, double valorTotal) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal < 0) {
            throw new IllegalArgumentException("O valor total da venda não pode ser negativo.");
        }
        this.valorTotal = valorTotal;
    }

    public void validate() {
        if (valorTotal <= 0) {
            throw new IllegalArgumentException("O valor total da venda deve ser maior que zero.");
        }
    }

    @Override
    public String toString() {
        return "Venda #" + id + " | Data: " + dataVenda + " | Total: R$ " + String.format("%.2f", valorTotal);
    }
}
