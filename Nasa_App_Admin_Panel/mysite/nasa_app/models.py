from django.db import models
from  multiselectfield import MultiSelectField
import requests


# Create your models here.
api_end_point = 'http://172.22.0.37:3000'
# save the get data


class Items(models.Model):
    item_name = models.CharField(max_length =100, blank=False)
    item_weight = models.SmallIntegerField(blank=False)
    item_description = models.TextField(blank=False)
    item_quantity_min  = models.SmallIntegerField(blank=False)
    item_quantity_max = models.SmallIntegerField(blank=False)
    item_expiry_duration = models.SmallIntegerField(blank=False)
    item_mongo_db_id = models.CharField(max_length= 100, blank=False)
    def __str__(self):
        return self.item_name

    class Meta:
        verbose_name_plural= "Items"

    def save(self, *args, **kwargs):
        data = {
        "item_name":self.item_name,
        "item_wt":self.item_weight,
        "item_desc":self.item_description,
        "item_qty_min":self.item_quantity_min,
        "item_qty_max":self.item_quantity_max,
        "item_exp_date":self.item_expiry_duration
        }
        returnData = requests.post("http://172.22.0.37:3000/admin/item", data = data)
        if returnData.status_code == 200:
            # itemsData = requests.get('http://172.22.0.37:3000/admin/item')
            #             # if itemsData.status_code == 200:
            #             #     r = itemsData.json()
            #             #     for i in range(len(r)):
            #             #         print(r[i]['item_name'])
                    # Items.objects.create(item_name=r[i]['item_name'], item_weight=r[i]['item_wt'], item_description=r[i]['item_desc'], item_quantity_min=r[i]['item_qty_min'], item_quantity_max=r[i]['item_qty_max'], item_expiry_duration=r[i]['item_exp_date'], item_mongo_db_id=r[i]['_id'])
                    # p = Items(item_name=r[i]['it em_name'], item_weight=r[i]['item_wt'],
                    #                      item_description=r[i]['item_desc'], item_quantity_min=r[i]['item_qty_min'],
                    #                      item_quantity_max=r[i]['item_qty_max'],
                    #                      item_expiry_duration=r[i]['item_exp_date'], item_mongo_db_id=r[i]['_id'])
            r1 = returnData.json()
            self.item_mongo_db_id = r1['_id']
            super(Items, self).save(*args, **kwargs)
        else:
            print("Errr" + returnData.status_code)


    def delete(self, *args, **kwargs):
        # super(Items, self).delete(*args, **kwargs)
        try:
            returnData = requests.delete("http://172.22.0.37:3000/admin/item/" + str(self.item_mongo_db_id))
            if returnData.status_code == 200:
                super(Items, self).delete(*args, **kwargs)
            else:
                print("Cannot Perform Action")
        except:
            print("Error")





class Disaster(models.Model):
    ItemsObject = Items.objects.all()
    disater_mongo_db_id = models.CharField(max_length=100, blank=True)
    disaster_name = models.CharField(max_length=100, blank=False)
    disaster_description = models.TextField(blank=False)
    disaster_image = models.ImageField(upload_to="nasa_app/static/nasa_app/img/", blank = True)
    disaster_background_image = models.ImageField(upload_to="nasa_app/static/nasa_app/img/", blank = True)
    disaster_video_link =  models.URLField(blank = False)
    disater_evac_steps = models.TextField(blank= False)
    disaster_key_factor = models.TextField(blank =False)

    class Meta:
        verbose_name_plural = "Disaster"

    def __str__(self):
        return self.disaster_name

    #override the save method
    def save(self, *args, **kwargs):

        # headers = {}
        # register_openers()
        # data = {'media' : open( img ),
        #     'additionalattr': 111,}
        # datagen, headers = multipart_encode(data)
        # headers['Connection']='keep-alive'
        # request = urllib2.Request('%s/upload_image/' % ( server ), datagen, headers)
        #
        # self.disater_mongo_db_id = "dasdasd"
        # print(str(self.disaster_image))
        times = []
        super(Disaster, self).save(*args, **kwargs)
        print(str(self.disaster_image.url))
        # content_type = 'image/jpeg'
        # headers = {'content-type': content_type}
        # img = cv2.imread('./' + str(self.disaster_image))
        # # encode image as jpeg
        # _, img_encoded = cv2.imencode('.jpg', img)
        # img = cv2.imread('./' + str(self.disaster_background_image))
        # _, img_encoded1 = cv2.imencode('.jpg', img)
        # # send http request with image and receive response
        #     data= {
        #     "disaster_name":self.disaster_name,
        #     "disaster_desc":self.disaster_description,
        #     "disaster_image": img_encoded.tostring(),
        #     "disaster_background_image":img_encoded1.tostring(),
        #     "disaster_video":self.disaster_video_link,
        #     "evac_steps":self.disater_evac_steps.split(','),
        #     "disaster_key_factors":self.disaster_key_factor.split(',')
        #     }
        # response = requests.post(api_end_point+"/admin/disaster", data=data, headers=headers)
        # print(response)
# decode response
        # print json.loads(response.text)
        # image= Image.open('./'+ str(self.disaster_image))
        # times.append(str(int(time.time())))
        # image.save("Compressed_"+times[-1],"JPEG",optimize=True,quality=85)
        # print(str(self.disaster_image))



        # print(str(self.disaster_image))
        # datagen, headers= multipart_encode({
        # "disaster_name":self.disaster_name,
        # "disaster_desc":self.disaster_description,
        # "disaster_image": open('./' + str(self.disaster_image), "rb"),
        # "disaster_background_image":open('./'+ str(self.disaster_background_image),"rb"),
        # "disaster_video":self.disaster_video_link,
        # "evac_steps":self.disater_evac_steps.split(','),
        # "disaster_key_factors":self.disaster_key_factor.split(',')
        # })

        # data= {
        # "disaster_name":self.disaster_name,
        # "disaster_desc":self.disaster_description,
        # "disaster_image": open('./' + str(self.disaster_image), "rb"),
        # "disaster_background_image":open('./'+ str(self.disaster_background_image),"rb"),
        # "disaster_video":self.disaster_video_link,
        # "evac_steps":self.disater_evac_steps.split(','),
        # "disaster_key_factors":self.disaster_key_factor.split(',')
        # }
        #
        # request = urllib3.Request("http://172.22.0.37:3000/admin/disaster/", datagen, headers)
        # print(urllib3.urlopen(request).read())
        # return_data = requests.post(url=api_end_point+"/admin/disaster", data=datas)
        # if(returnData.status_code == 200):
        #     r = returnData.json()
        #     super(Disaster, self).save(*args, **kwargs)
        # else:
        #     print("Error Performing action" + str(returnData.status_code))

    # to override the delete method
    # def delete(self, *args, **kwargs):
    #     try:
    #         return_data = requests.delete(url = api_end_point+"/admin/disaster/"+self.disater_mongo_db_id)
    #         if return_data.status_code == 200:
    #             super(Disaster, self).save(*args, **kwargs)
    #         else:
    #             print("Not able to perfrom" + str(return_data.status))
    #     except:
    #         print("Error")

class Location(models.Model):
    disasterData = requests.get('http://172.22.0.37:3000/admin/disaster')
    if(disasterData.status_code == 200):
        disasterDataJson = disasterData.json()
        listsData =[]
        r = disasterData.json()
        for i in range(len(r)):
            tempTuple = (str(r[i]['_id']), str(r[i]['disaster_name']))
            listsData.append(tempTuple)
        MY_CHOICES = tuple(listsData)
        state_name = models.CharField(max_length=80, blank=False, verbose_name ="State Name")
        police_number = models.PositiveIntegerField(blank=True)
        disaster = MultiSelectField(choices = MY_CHOICES)
        fire_brigade_number = models.PositiveIntegerField(blank=True)
        ambulance_number = models.PositiveIntegerField(blank=True)
        disaster_number = models.PositiveIntegerField(blank = True)
        disaster_mongo_db_id = models.CharField(max_length = 10, blank = False)
    else:
        print("Error " + disasterData.status_code)

    def __str__(self):
        return  self.state_name

    class Meta:
        verbose_name_plural= "Locations"

    def save(self, *args, **kwargs):
        disaster_d =[]
        for i in self.disaster:
            disaster_d.append({'id':i})
        contacts_d = []
        contacts_d.append({'ambulance':self.ambulance_number})
        contacts_d.append({'disaster':self.disaster_number})
        contacts_d.append({'police':self.police_number})
        contacts_d.append({'fire':self.fire_brigade_number})

        data = {
            'state_name':self.state_name,
            'disasters': disaster_d,
            'contacts':contacts_d
        }
        returnData = requests.post('http://172.22.0.37:3000/admin/loc', data = data)
        if returnData.status_code == 200:
            r = returnData.json()
            self.disaster_mongo_db_id = r['_id']
            super(Location, self).save(*args, **kwargs)
        else:
            print("Error"+ returnData.status_code)
