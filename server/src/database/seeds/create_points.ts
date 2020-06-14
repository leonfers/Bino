import Knex from 'knex';

export async function seed(knex: Knex){
    await knex('points').insert([
    {name: 'Posto São José', latitude:'35.4562', longitude: '25.4578', description: 'Posto de combustível', date: '', avaliation: 'true', visible: 'true'},
    {name: 'Ponto Sul', latitude:'-12.4562', longitude: '-25.4578', description: 'Ponto de parada', date: '', avaliation: 'true', visible: 'true'},
    {name: 'Restaurante Seu Sabor', latitude:'-10.1232', longitude: '-47.2312', description: 'Restaurante', date: '', avaliation: 'false', visible: 'true'},
    {name: 'Manifestação na Avenida Principal', latitude:'35.4562', longitude: '-23.4518', description: 'Evento', date: '2020-05-29T12:00:00.000Z', avaliation: 'true', visible: 'true'},
    {name: 'Posto P', latitude:'-30.8531', longitude: '-50.2478', description: 'Posto de combustível', date: '', avaliation: 'true', visible: 'true'},
    {name: 'Ponto Arriscado', latitude:'-0.1562', longitude: '25.4578', description: 'Ponto de parada', date: '', avaliation: 'false', visible: 'true'},
    ])
}