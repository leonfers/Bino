import Knex from 'knex';

export async function seed(knex: Knex) {
    await knex('points').insert([
        { name: 'Avenida Interditada por Protestos', latitude: '-24.5040', longitude: '-48.8419', description: 'Avenida Interditada por Protestos', date: '', avaliation: 'true', visible: 'true' },
        { name: 'Restaurante Seu Sabor',latitude: '-23.6227', longitude: '-48.0900' , description: 'Restaurante Seu Sabor', date: '', avaliation: 'false', visible: 'true' },
        { name: 'Ponto De Apoio Estrada para Saúde', latitude: '-24.4710', longitude: '-48.8399', description: 'Ponto De Apoio Estrada para Saude', date: '', avaliation: 'true', visible: 'true' },
        { name: 'Área com bom sinal de internet, para se comunicar com a Família ', latitude: '-24.4908', longitude: '-48.8453', description: 'Area com bom sinal de internet, para se comunicar com a familia', date: '', avaliation: 'false', visible: 'true' },
    ])
}