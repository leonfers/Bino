import Knex from 'knex';

export async function up(knex: Knex){
    return knex.schema.createTable('points', table =>{
        table.increments('id').primary();
        table.string('name').notNullable();
        table.decimal('latitude').notNullable();
        table.decimal('longitude').notNullable();
        table.string('description').notNullable();
        table.boolean('avaliation').notNullable();
        table.boolean('visible').notNullable();
        table.dateTime('date');
    });

}

export async function down(knex: Knex){
    return knex.schema.dropTable('points');
}
