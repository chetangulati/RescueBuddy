# Generated by Django 2.1.2 on 2018-10-20 21:59

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('nasa_app', '0005_items_item_expiry_duration'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='location',
            options={'verbose_name_plural': 'Locations'},
        ),
        migrations.RemoveField(
            model_name='location',
            name='my_field',
        ),
    ]