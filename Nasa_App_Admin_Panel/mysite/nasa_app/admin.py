from django.contrib import admin

# Register your models here.
from .models import (Items, Disaster, Location)

# Register your models here.
# Custom Class for Disaster Model
# @admin.register(Disaster)
# class DisasterAdmin(admin.ModelAdmin):
#     field =['disaster_name', 'disaster_description', 'disaster_image', 'disaster_video_link', 'disater_evac_steps']
#     readonly_fields = ['disater_mongo_db_id']

# Custom Class for Items Models
@admin.register(Items)
class ItemsAdmin(admin.ModelAdmin):
    field =['item_name', 'item_description', 'item_weight', 'item_quantity_min', 'item_quantity_max', 'item_expiry_duration']
    readonly_fields = ['item_mongo_db_id']
    list_display = ('item_name', 'item_weight', 'item_expiry_duration')

@admin.register(Location)
class LocationAdmin(admin.ModelAdmin):
    field =['state_name', 'police_number', 'disaster','fire_brigade_number', 'ambulance_number', 'disaster_number']
    readonly_fields = ['disaster_mongo_db_id']
    list_display = ('state_name', 'police_number', 'fire_brigade_number', 'ambulance_number','disaster_number')

# admin.site.register(Disaster)